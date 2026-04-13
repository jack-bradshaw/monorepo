package com.jackbradshaw.backstab.ksp.repository

import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.validate
import com.jackbradshaw.backstab.core.CoreScope
import com.jackbradshaw.backstab.core.model.BackstabModule
import com.jackbradshaw.backstab.core.model.BackstabTarget
import com.jackbradshaw.backstab.core.typeregistry.BackstabTypeRegistry
import com.jackbradshaw.backstab.ksp.parser.Parser
import com.jackbradshaw.oksp.model.SourceFile
import com.jackbradshaw.oksp.application.ApplicationComponent
import com.jackbradshaw.oksp.service.ProcessingService
import javax.inject.Inject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.CoroutineScope
import com.jackbradshaw.coroutines.io.Io
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import com.jackbradshaw.backstab.core.repository.Repository as CoreRepository
import com.jackbradshaw.backstab.ksp.repository.Repository



/* 
whats the issue

when simply transforming streams the transformer class can be stateless, and doesnt need any
internal long running operations

like funThing(): Flow<Foo> = otherThing.getBarFlow().map { it * 2 }

that approach is great because the class is just accepting flows, transforming them, and passing
them to the consumer. the ocnsumer holds flow control so when something contextual happens they
can cancel.

why doesnt that work here?

because the flows arent just transformed directly without side effects. for example, the target
flow should only contain valid targets, but invalid targets need to be deferred to the next round
so what does the filtering? it could be a side effect of the target flow, but that has its onw
issues. flows should be idempotent and there should be no problem opening multiple flows, but does
that mean the symbols get deferred repeatedly? it's messy and it creates other problems'. 

a better solution is required. despite the fact that the upstream and the downstream are both
using flows, they have fundamentally different expectations. downstream expects to just open a flow
of targets, observe them, call back with modules and errors, and operate statelessly, whereas the
upstream is inherently stateful, meaning it operates on rounds, requires various conditiosn before
a round can complete. there is more logic to it (eg cannot both provide an error and a module
for a target in a single round etc) but the point is, there is coordination logic that goes
beyond a simple stream transformation and it has side effects. this class needs a lifecycle so it
can manage memory, perform internal operations to move data out of upstream flows, process it, and
emit domain flows. 

so whats the problem? give it a lifecycle. 

well... the problem is my library for lifecycle management isnt complete yet, so do i push that
onto the stack and delay this work, setting me back, and getting in the way of the 1000 other things
i want to do? or do i come up with some hacky lifecycle system here?

it's just one class, go with the hacky lifecycle solution. similar to main.

1. create a managed type in ksp with a suspend fun run function.
2. implement it in repositoryimp
3. bind it into a multibing set
4. inject all such managed things into application
5. start/stop them there

that's basically what the lifecycle manager system would do just less elegantly but it doesnt need
to be a completely reusable system, just add a comment saying TODO replace with codestone when
complete and link to a bug that says "Implement Codestone"

qq why not just launch a coroutine in side repositoryimpl then cancel the scope later?
that is a solution i see some people use but it has issues. it requires the root lifecycle manager
to operate on the implementation details of various classes and is error prone. which order do
scopes get cancelled in, and what happens if they requrie specific ordering to prevent strange
shutdown effects? furthermore what happens if someone switches the impl so it never exposes its
scope to begin with? the api should expose a cancellation system (suspendable run that can be
cancelled) so the consumer can control it via its API not its implementation details
 
*/





/** The KSP processor for the Backstab annotation processor. */
@CoreScope
class RepositoryImpl
@Inject
constructor(
    private val parser: Parser,
    private val processingService: ProcessingService,
    @Io private val ioScope: CoroutineScope,
) : Repository, CoreRepository {

  private val currentRound = MutableStateFlow<Round?>(null)
  
  override suspend fun run() {
    val job1 = ioScope.launch { updateCurrentRoundOnChange() }
    val job2 = ioScope.launch { notifyProcessingServiceOnRoundComplete() }
    job1.join()
    job2.join()
  }

  @OptIn(ExperimentalCoroutinesApi::class)
  override fun observeTargets(): Flow<BackstabTarget> = currentRound
    .filterNotNull()
    .map { it.targets }
    .distinctUntilChanged()
    .flatMapConcat { it.asFlow() }

  override suspend fun publishModule(target: BackstabTarget, module: BackstabModule) {
    processingService.publishSource(module.sourceFile, listOf(getAnchor(target)))
    currentRound.update { it?.copyWithIncrementedProcessedCount() }
  }

  override suspend fun publishError(target: BackstabTarget, error: Throwable) {
    processingService.publishError(error, getAnchor(target))
    currentRound.update { it?.copyWithIncrementedProcessedCount() }
  }

  /** Maps incoming rounds from upstream to a new [Round] object and updates [currentRound].
   * Suspends indefinitely.
   */
  private suspend fun updateCurrentRoundOnChange() {
    processingService
            .observeRoundStartEvents()
            .map { initialiseRound(processingService.getRoundResolver()) }
            .onEach { currentRound.value = it }
            .collect()
  }

/** Monitors [currentRound] and notifies [processingService] each time a round completes.
 * Suspends indefinitely. */
  private suspend fun notifyProcessingServiceOnRoundComplete() {
    val roundChanged = 
      currentRound
      .filterNotNull()
      .map {
        it.targets.size == it.targetsProcessedCount
      }
      .distinctUntilChanged()
      .filter { it == true }
    
    
    roundChanged.onEach {
      processingService.completeRound()
    }.collect()
  }

  private suspend fun initialiseRound(resolver: com.google.devtools.ksp.processing.Resolver): Round {
    val allBackstabSymbols = resolveBackstabAnnotatedClasses(resolver)
 
    /* The last response to `publishError` or `publishModule` will end the round immediately, so
     * pre-publishing all invalid symbols as deferred entries ahead of time ensures they are not
     * missed. */
    for (invalidSymbol in resolveInvalidSymbols(allBackstabSymbols)) {
      processingService.publishDeferred(invalidSymbol)
    }
    
    val targets = mutableListOf<BackstabTarget>()
    val targetsToAnchors = mutableMapOf<BackstabTarget, KSClassDeclaration>()
    
    for (symbol in resolveValidSymbols(allBackstabSymbols)) {
      val target = parser.toBackstabTarget(symbol)
      targetsToAnchors[target] = symbol
      targets.add(target)
    }
    
    return Round(targets, targetsToAnchors.toMap())
  }

  private fun getAnchor(target: BackstabTarget): KSClassDeclaration  {
    val round =  checkNotNull(currentRound.value) {
      "RepositoryImpl is not presently processing a round."
    }

  return checkNotNull(round.targetsToAnchors?.get(target)) {
          "Target $target is no longer available for processing. The API backing this repository " +
          "has proceeded to a new round and discarded the target. Ensure there is exactly one " +
          "call to `publishModules` or `publishError` for each target to prevent this error."
        }
      }

  private fun resolveBackstabAnnotatedClasses(resolver: com.google.devtools.ksp.processing.Resolver): List<KSAnnotated> {
    return resolver.getSymbolsWithAnnotation(BackstabTypeRegistry.BACKSTAB.qualifiedName!!).toList()
  }

  private fun resolveValidSymbols(symbols: List<KSAnnotated>): List<KSClassDeclaration> {
    return symbols.filterIsInstance<KSClassDeclaration>().filter { it.validate() }
  }

  private fun resolveInvalidSymbols(symbols: List<KSAnnotated>): List<KSAnnotated> {
    return symbols.filter { if (it is KSClassDeclaration) !it.validate() else true }
  }
  private data class Round(
    val targets: List<BackstabTarget>,
    val targetsToAnchors: Map<BackstabTarget, KSClassDeclaration> = emptyMap(),
    val targetsProcessedCount: Int = 0,
  ) {
    fun copyWithIncrementedProcessedCount() = copy(
      targetsProcessedCount = targetsProcessedCount + 1
    )
  }
}