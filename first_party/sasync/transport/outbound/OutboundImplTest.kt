package com.jackbradshaw.sasync.transport.outbound

import com.jackbradshaw.coroutines.testing.DaggerTestCoroutines
import com.jackbradshaw.coroutines.testing.TestCoroutines
import com.jackbradshaw.sasync.SasyncScope
import com.jackbradshaw.sasync.transport.outbound.config.Config
import com.jackbradshaw.sasync.transport.outbound.config.config
import dagger.BindsInstance
import dagger.Component
import java.io.ByteArrayOutputStream
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestScope
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class OutboundImplTest : OutboundTest() {

  private val destination = ByteArrayOutputStream()

  private lateinit var testScope: TestScope

  private lateinit var subject: Outbound

  override fun setup(config: Config) {
    val component =
        DaggerTestComponent.builder()
            .setConfig(config)
            .setCoroutines(DaggerTestCoroutines.create())
            .build()

    testScope = component.testScope()
    subject = component.outboundFactory().create(destination)

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

@SasyncScope
@Component(dependencies = [TestCoroutines::class])
interface TestComponent {
  fun outboundFactory(): OutboundImplFactory

  fun testScope(): TestScope

  @Component.Builder
  interface Builder {
    @BindsInstance fun setConfig(config: Config): Builder

    fun setCoroutines(coroutines: TestCoroutines): Builder

    fun build(): TestComponent
  }
}
