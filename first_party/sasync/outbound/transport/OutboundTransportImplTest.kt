package com.jackbradshaw.sasync.outbound.transport

import com.jackbradshaw.coroutines.testing.DaggerTestCoroutines
import com.jackbradshaw.coroutines.testing.TestCoroutines
import com.jackbradshaw.sasync.outbound.OutboundScope
import com.jackbradshaw.sasync.outbound.config.Config
import com.jackbradshaw.sasync.outbound.config.config
import dagger.BindsInstance
import dagger.Component
import java.io.ByteArrayOutputStream
import javax.inject.Inject
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestScope
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class OutboundTransportImplTest : OutboundTransportTest() {

  @Inject lateinit var testScope: TestScope

  @Inject lateinit var subjectFactory: OutboundTransportImpl.Factory

  private val destination = ByteArrayOutputStream()

  private lateinit var subject: OutboundTransport

  override fun setup(config: Config) {
    DaggerTestComponent.builder()
        .binding(config)
        .consuming(DaggerTestCoroutines.create())
        .build()
        .inject(this)

    subject = subjectFactory.create(destination)

    runBlocking { waitForIdle() }
  }

  override fun subject() = subject

  override fun received(): ByteArray {
    val bytes = destination.toByteArray()
    destination.reset()
    return bytes
  }

  override fun testScope() = testScope
}

@OutboundScope
@Component(dependencies = [TestCoroutines::class])
interface TestComponent {
  fun inject(target: OutboundTransportImplTest)

  @Component.Builder
  interface Builder {
    @BindsInstance fun binding(config: Config): Builder

    fun consuming(coroutines: TestCoroutines): Builder

    fun build(): TestComponent
  }
}
