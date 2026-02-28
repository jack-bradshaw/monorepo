package com.jackbradshaw.sasync.inbound.transport

import com.jackbradshaw.concurrency.pulsar.Pulsar
import com.jackbradshaw.coroutines.io.Io
import com.jackbradshaw.sasync.inbound.config.Config
import com.jackbradshaw.universal.frequency.Frequency
import com.jackbradshaw.universal.frequency.toHertz
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import java.io.InputStream
import java.time.Duration
import kotlin.time.Duration as DurationKt
import kotlin.time.toKotlinDuration
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.flow.transformWhile

class InboundTransportImpl
@AssistedInject
constructor(
    private val config: Config,
    @Io private val ioScope: CoroutineScope,
    private val pulsar: Pulsar,
    @Assisted private val source: InputStream,
) : InboundTransport {

  /**
   * The period between poll events, null for instantaneous. Evaluated during init to validate
   * arguments.
   */
  private val refreshRateDuration: DurationKt? =
      when (config.refreshRate.typeCase) {
        Frequency.TypeCase.UNBOUNDED -> null
        Frequency.TypeCase.BOUNDED ->
            Duration.ofMillis((1000.0 / config.refreshRate.bounded.toHertz()).toLong())
        else -> throw IllegalArgumentException("refreshRate size not set in config: ${config}")
      }?.toKotlinDuration() ?: null

  /**
   * A reusable buffer for read calls. Possible because flows are shared so only one thread will be
   * accessing the buffer at any given time.
   */
  private val reusableBuffer = ByteArray(config.bufferSize.value.toInt())

  /**
   * A flow that reads from [source].
   *
   * The flow operates are created such that as much conditional logic is moved to creation-time not
   * flow-time in order to improve high-frequency performance.
   */
  private val sharedBufferedFlow: SharedFlow<ByteArray> by lazy {
    createRefreshPulse()
        .transformWhile<Unit, ByteArray> {
          val count = source.read(reusableBuffer)
          if (count == -1) {
            END_FLOW
          } else if (count == 0) {
            CONTINUE_FLOW
          } else {
            emit(reusableBuffer.copyOf(newSize = count))
            CONTINUE_FLOW
          }
        }
        .shareIn(
            ioScope,
            SharingStarted.WhileSubscribed(stopTimeoutMillis = 0, replayExpirationMillis = 0),
            replay = 0)
  }

  private val sharedFlattenedFlow: SharedFlow<Byte> by lazy {
    sharedBufferedFlow
        .transform<ByteArray, Byte> { array -> array.forEach { emit(it) } }
        .shareIn(
            ioScope,
            SharingStarted.WhileSubscribed(stopTimeoutMillis = 0, replayExpirationMillis = 0),
            replay = 0)
  }

  override fun observeBuffered() = sharedBufferedFlow

  override fun observeFlattened() = sharedFlattenedFlow

  /**
   * Create a cold flow which emits units indefinitely with a delay between each for the refresh
   * rate.
   */
  private fun createRefreshPulse(): Flow<Unit> =
      if (refreshRateDuration == null) {
        pulsar.pulses()
      } else {
        pulsar.pulses().map {
          delay(refreshRateDuration)
          it
        }
      }

  companion object {
    private const val CONTINUE_FLOW = true
    private const val END_FLOW = false
  }

  @AssistedFactory
  interface Factory {
    fun create(source: InputStream): InboundTransportImpl
  }
}
