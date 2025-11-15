package com.jackbradshaw.sasync.inbound.transport

import com.jackbradshaw.concurrency.pulsar.testing.TestPulsar
import com.jackbradshaw.concurrency.testing.DaggerTestConcurrency
import com.jackbradshaw.concurrency.testing.TestConcurrency
import com.jackbradshaw.coroutines.testing.DaggerTestCoroutines
import com.jackbradshaw.coroutines.testing.TestCoroutines
import com.jackbradshaw.model.count.Count
import com.jackbradshaw.model.frequency.Frequency
import com.jackbradshaw.model.frequency.FrequencyKt
import com.jackbradshaw.model.frequency.frequency
import com.jackbradshaw.sasync.inbound.InboundScope
import com.jackbradshaw.sasync.inbound.config.Config
import com.jackbradshaw.sasync.inbound.config.config
import dagger.BindsInstance
import dagger.Component
import java.io.InputStream
import javax.inject.Inject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runCurrent
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
@OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
class InboundTransportImplTest : InboundTransportTest() {

  @Inject lateinit var testScope: TestScope

  @Inject lateinit var pulsar: TestPulsar

  @Inject lateinit var subjectFactory: InboundTransportImpl.Factory

  private val source = FakeInputStream()

  private lateinit var subject: InboundTransport

  override fun setupSubject(bufferSize: Count.Bounded) {
    setupSubject(bufferSize, frequency { unbounded = FrequencyKt.unbounded {} })
  }

  private fun setupSubject(bufferSize: Count.Bounded, refreshRate: Frequency) {
    val config = config {
      this.bufferSize = bufferSize
      this.refreshRate = refreshRate
    }

    DaggerTestComponent.builder()
        .binding(config)
        .consuming(DaggerTestCoroutines.create())
        .consuming(DaggerTestConcurrency.create())
        .build()
        .inject(this)

    subject = subjectFactory.create(source)
  }

  override fun subject() = subject

  override fun queue(byte: Byte) {
    runBlocking { source.channel.send(byte) }
  }

  override fun advanceThroughNextBuffer() {
    runBlocking {
      testScope().launch(UnconfinedTestDispatcher(testScope().testScheduler)) { pulsar.emit() }
      testScope().runCurrent()
    }
  }

  override fun testScope() = testScope

  override fun waitUntilIdle() {
    runBlocking { pulsar.emit() }
    super.waitUntilIdle()
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
@Component(dependencies = [TestCoroutines::class, TestConcurrency::class])
interface TestComponent {
  fun inject(target: InboundTransportImplTest)

  @Component.Builder
  interface Builder {
    @BindsInstance fun binding(config: Config): Builder

    fun consuming(coroutines: TestCoroutines): Builder

    fun consuming(concurrency: TestConcurrency): Builder

    fun build(): TestComponent
  }
}
