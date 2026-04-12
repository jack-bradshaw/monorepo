package com.jackbradshaw.sasync.outbound.transport

import com.jackbradshaw.coroutines.testing.realistic.RealisticCoroutinesTestingComponent
import com.jackbradshaw.coroutines.testing.realistic.realisticCoroutinesTestingComponent
import com.jackbradshaw.sasync.outbound.OutboundScope
import com.jackbradshaw.sasync.outbound.config.Config
import com.jackbradshaw.sasync.outbound.config.config
import dagger.BindsInstance
import dagger.Component
import java.io.ByteArrayOutputStream
import javax.inject.Inject
import kotlinx.coroutines.runBlocking
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class OutboundTransportImplTest : OutboundTransportTest() {

  @Inject lateinit var subjectFactory: OutboundTransportImpl.Factory

  private val destination = ByteArrayOutputStream()

  private lateinit var subject: OutboundTransport

  override fun setup(config: Config) {
    DaggerTestComponent.builder()
        .binding(config)
        .consuming(realisticCoroutinesTestingComponent())
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
}

@OutboundScope
@Component(dependencies = [RealisticCoroutinesTestingComponent::class])
interface TestComponent {
  fun inject(target: OutboundTransportImplTest)

  @Component.Builder
  interface Builder {
    @BindsInstance fun binding(config: Config): Builder

    fun consuming(coroutines: RealisticCoroutinesTestingComponent): Builder

    fun build(): TestComponent
  }
}
