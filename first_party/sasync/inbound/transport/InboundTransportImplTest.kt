package com.jackbradshaw.sasync.inbound.transport

import com.jackbradshaw.concurrency.pulsar.testing.TestPulsar
import com.jackbradshaw.concurrency.testing.TestConcurrencyComponent
import com.jackbradshaw.concurrency.testing.testConcurrencyComponent
import com.jackbradshaw.coroutines.testing.realistic.RealisticCoroutinesTestingComponent
import com.jackbradshaw.coroutines.testing.realistic.realisticCoroutinesTestingComponent
import com.jackbradshaw.sasync.inbound.InboundScope
import com.jackbradshaw.sasync.inbound.config.Config
import com.jackbradshaw.sasync.inbound.config.config
import com.jackbradshaw.universal.count.Count
import com.jackbradshaw.universal.frequency.Frequency
import com.jackbradshaw.universal.frequency.FrequencyKt
import com.jackbradshaw.universal.frequency.frequency
import dagger.BindsInstance
import dagger.Component
import java.io.InputStream
import javax.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
@OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
class InboundTransportImplTest : InboundTransportTest() {

  @Inject lateinit var pulsar: TestPulsar

  @Inject lateinit var subjectFactory: InboundTransportImpl.Factory

  private val source = FakeInputStream()

  private lateinit var subject: InboundTransport

  override fun setupSubject(bufferSize: Count.Bounded) {
    setupSubject(bufferSize, frequency { unbounded = FrequencyKt.unbounded {} })
  }

  override fun subject() = subject

  override fun queue(byte: Byte) {
    runBlocking { source.channel.send(byte) }
  }

  override fun advanceThroughNextBuffer() {
    runBlocking {
      CoroutineScope(cpuContext).launch { pulsar.emit() }
      waitUntilIdle()
    }
  }

  override suspend fun waitUntilIdle() {
    pulsar.emit()
    super.waitUntilIdle()
  }

  private fun setupSubject(bufferSize: Count.Bounded, refreshRate: Frequency) {
    val config = config {
      this.bufferSize = bufferSize
      this.refreshRate = refreshRate
    }

    DaggerTestComponent.builder()
        .binding(config)
        .consuming(realisticCoroutinesTestingComponent())
        .consuming(testConcurrencyComponent())
        .build()
        .inject(this)

    subject = subjectFactory.create(source)
  }

  inner class FakeInputStream() : InputStream() {

    var channelSize: Int = 0
    var channel = Channel<Byte>(Channel.UNLIMITED)

    override fun read(buffer: ByteArray): Int {
      var readCount = 0
      runBlocking {
        for (i in 0 until buffer.size) {
          val readValue = channel.tryReceive()
          readValue.getOrNull()?.let {
            buffer[i] = it
            readCount++
          } ?: break
        }
      }

      return readCount
    }

    override fun read(): Int {
      // Required by contract but not used in test.
      throw UnsupportedOperationException()
    }
  }

  companion object {
    private val REFRESH_RATE_UNBOUNDED = config {
      bufferSize = DEFAULT_BUFFER_SIZE
      refreshRate = frequency { unbounded = FrequencyKt.unbounded {} }
    }
    private val REFRESH_RATE_BOUNDED = config {
      bufferSize = DEFAULT_BUFFER_SIZE
      refreshRate = frequency { bounded = FrequencyKt.bounded { hertz = 10.0 } }
    }
  }
}

@InboundScope
@Component(
    dependencies = [RealisticCoroutinesTestingComponent::class, TestConcurrencyComponent::class])
interface TestComponent {
  fun inject(target: InboundTransportImplTest)

  @Component.Builder
  interface Builder {
    @BindsInstance fun binding(config: Config): Builder

    fun consuming(coroutines: RealisticCoroutinesTestingComponent): Builder

    fun consuming(concurrency: TestConcurrencyComponent): Builder

    fun build(): TestComponent
  }
}
