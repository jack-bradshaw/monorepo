package com.jackbradshaw.obelisk.oksp.service

import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSNode
import com.jackbradshaw.coroutines.Io
import com.jackbradshaw.obelisk.core.adapters.InflowAdapter
import com.jackbradshaw.obelisk.core.adapters.Ingestion
import com.jackbradshaw.obelisk.core.adapters.OutflowAdapter
import com.jackbradshaw.obelisk.core.model.LogLevel
import com.jackbradshaw.obelisk.core.model.Source
import com.jackbradshaw.oksp.model.LogLevel as OkspLogLevel
import com.jackbradshaw.oksp.model.Source as OkspSource
import com.jackbradshaw.oksp.service.KspService
import com.jackbradshaw.sluice.Sluice
import com.jackbradshaw.sluice.SluiceFactory
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/** The KSP processor for the Backstab annotation processor. */
class ServiceImpl<A, R>(
    private val inflowAdapter: InflowAdapter<KSNode, A>,
    private val outflowAdapter: OutflowAdapter<R>,
    private val kspService: KspService,
    private val sluiceFactory: SluiceFactory,
    @Io private val ioDispatcher: CoroutineDispatcher,
) : Service<A, R> {

  /** Map to reverse lookup [KsNode]s from the values they were translated to. Associations are only
   * valid during a round and the map must be cleared at the end of the round. The values should be
   * passed by to KSP and must otherwise have minimal interactions, as KSP is strictly not
   * multi-threaded and using its types outside KSP is extremely error prone.
   */
  private val anchors = mutableMapOf<A, Set<KSNode>>()

  private val translations = MutableSharedFlow<A>()

  private val processingScopeHandle = Job()

  private val processingScope = CoroutineScope(ioDispatcher + processingScopeHandle)

  private data class RoundState(
      val targetCount: Int = 0, 
      val processedCount: Int = 0,
      val id: java.util.UUID = java.util.UUID.randomUUID()
  )
  private val currentRound = MutableStateFlow<RoundState?>(null)

  init {
    processingScope.launch {
      kspService.onEachRoundStart()
        .onEach { 
          // Ensures KSP types are not retained over round boundaries.
          anchors.clear()
          
          updateForLatestIngestion(ingest()) 
        }
        .collect()
    }

    processingScope.launch {
      currentRound
        .filterNotNull()
        .map { it.targetCount == it.processedCount }
        .filter { it }
        .onEach { kspService.completeRound() }
        .collect()
    }

    processingScope.launch {
      kspService.onFinalRoundComplete().collect {
        kspService.allowTermination()
        processingScopeHandle.cancel()
      }
    }
  }

  override suspend fun allowStart() {
    kspService.allowProcessing()
  }

  override suspend fun allowEnd() { 
    kspService.allowTermination()
  }

  override suspend fun allowAdvance() {
    kspService.completeRound()
  }

  override suspend fun forceAbort() {
    processingScopeHandle.cancelAndJoin()
    kspService.abortProcessing()
  }

  override fun createSluice(): Sluice<A> = sluiceFactory.createSluice(translations)

  override suspend fun publish(result: R, anchors: Set<A>) {
    val okspSource = outflowAdapter.format(result).let {
      OkspSource(it.fileName, it.extension, it.packageName, it.contents)
    }
    kspService.publish(okspSource, getAnchors(anchors).toList())
    currentRound.update { it?.copy(processedCount = it.processedCount + anchors.size) }
  }
  
  override suspend fun fail(error: Throwable, anchor: A?) { 
    kspService.fail(error, anchor?.let { getDefaultAnchor(it) })
    if (anchor != null) {
      currentRound.update { it?.copy(processedCount = it.processedCount + 1) }
    }
  }

  override suspend fun fail(error: String, anchor: A?) { 
    kspService.fail(error, anchor?.let { getDefaultAnchor(it) })
    if (anchor != null) {
      currentRound.update { it?.copy(processedCount = it.processedCount + 1) }
    }
  }

  override suspend fun log(message: String, level: LogLevel?, anchor: A?) {
    val okspLevel = level?.let {
      when (it) {
        LogLevel.INFO -> OkspLogLevel.INFO
        LogLevel.WARNING -> OkspLogLevel.WARNING
        LogLevel.ERROR -> OkspLogLevel.ERROR
      }
    }
    kspService.log(message, okspLevel, anchor?.let { getDefaultAnchor(it) })
  }

  private suspend fun ingest(): Ingestion<KSNode, A> {
    var ingestion: Ingestion<KSNode, A>? = null
    kspService.withContext { kspContext ->
      ingestion = inflowAdapter.ingest(kspContext.resolver.getNewFiles().toSet())
    }

    // Null not expected ever, but check is forced by withContext API.
    return ingestion ?: Ingestion() 
  }

  /** Updates [targets] and [anchors] for latest data [ingestion].
   * 
   * Two separate passes are used to fully update [anchors] before emitting anything to [targets]
   * to ensure anchors is fully populated before any downstream consumers receive the target data.
   * This prevents early calls to [fail], [log] and [publish] from pulling an incomplete set of
   * anchors under race conditions.
   */
  private suspend fun updateForLatestIngestion(ingestion: Ingestion<KSNode, A>) {
    for (unused in ingestion.unused) {
      if (unused is KSAnnotated) {
        kspService.defer(unused)
      }
    }
    
    // Collected to ensure each translation is emitted exactly once
    val uniqueTranslations = mutableSetOf<A>()
    
    for ((node, translatedSet) in ingestion.translated) {
      for (translation in translatedSet) {
        anchors[translation] = (anchors[translation] ?: emptySet()) + node
        uniqueTranslations.add(translation)
      }
    }
    
    // Wait until there is at least one active subscriber before emitting
    translations.subscriptionCount.first { it > 0 }

    // Targets can now be safely emitted since all anchors are stored and duplicates are eliminated.
    currentRound.value = RoundState(targetCount = uniqueTranslations.size, processedCount = 0)
    for (translation in uniqueTranslations) {
      translations.emit(translation)
    }
  }

  /** Gets the KSNode anchors associated with this [anchor]. */
  private fun getAnchors(anchor: A): Set<KSNode> {
    return checkNotNull(anchors[anchor]) {
      "$anchor is not a valid anchor."
    } 
  }

  /** Gets the KSNode anchors associated with all elements in [anchors]. */
  private fun getAnchors(anchors: Set<A>): Set<KSNode> = anchors.flatMap { getAnchors(it) }.toSet()

  /**
   * Note: KSP's underlying logger only supports a single anchor. If the higher level compiler 
   * requests a failure across a domain model mapped to multiple KSNode files/symbols, we simply 
   * pick the first one. Tagging one file is sufficient to surface the error to the developer.
   */
  private fun getDefaultAnchor(anchor: A): KSNode? = getAnchors(anchor).firstOrNull()

  /**
   * Same rationale as [getDefaultAnchor] single-arg variant.
   */
  private fun getDefaultAnchor(anchors: Set<A>): KSNode? = getAnchors(anchors).firstOrNull()

  class Factory @Inject constructor(
    private val kspService: KspService,
    private val sluiceFactory: SluiceFactory,
    @Io private val ioDispatcher: CoroutineDispatcher,
  ) : Service.Factory {
    override fun <A, R> create(inflow: InflowAdapter<KSNode, A>, outflow: OutflowAdapter<R>): Service<A, R> = ServiceImpl(inflow, outflow, kspService, sluiceFactory, ioDispatcher) 
  }
}
