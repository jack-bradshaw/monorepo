package com.jackbradshaw.sealant.hub

import com.jackbradshaw.chronosphere.testingtaskbarrier.TestingTaskBarrier
import com.jackbradshaw.closet.observable.standard.StandardObservableClosableComponent
import com.jackbradshaw.closet.observable.standard.standardObservableClosableComponent
import com.jackbradshaw.closet.resourcemanager.set.ResourceSetComponent
import com.jackbradshaw.closet.resourcemanager.set.resourceSetComponent
import com.jackbradshaw.coroutines.Io
import com.jackbradshaw.coroutines.testing.Coroutines
import com.jackbradshaw.coroutines.testing.realistic.RealisticCoroutinesTestingComponent
import com.jackbradshaw.coroutines.testing.realistic.realisticCoroutinesTestingComponent
import com.jackbradshaw.sealant.SealantScope
import com.jackbradshaw.sealant.session.SealedSession
import com.jackbradshaw.sealant.session.SealedSessionTest
import dagger.Component
import java.util.concurrent.atomic.AtomicInteger
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

/**
 * Verifies that all [SealedSession] instances produced by [SealedHubImpl] conform to
 * [SealedSessionTest].
 */
@RunWith(JUnit4::class)
class SealedSessionFromHubTest : SealedSessionTest<String>() {

  private val valueCounter = AtomicInteger(0)

  private val upstreamFlow = MutableSharedFlow<String>()

  private lateinit var subject: SealedSession<String>

  @Inject lateinit var sealedHubFactory: SealedHub.Factory

  @Inject @Coroutines lateinit var taskBarrier: TestingTaskBarrier

  @Inject @Io lateinit var ioDispatcher: CoroutineDispatcher

  @Before
  fun setUp() =
      runBlocking<Unit> {
        val coroutines = realisticCoroutinesTestingComponent()
        val closables = standardObservableClosableComponent()
        DaggerSealedSessionFromHubTest_TestComponent.builder()
            .resourceSetComponent(resourceSetComponent(coroutines, closables))
            .realisticCoroutinesTestingComponent(coroutines)
            .standardObservableClosableComponent(closables)
            .build()
            .inject(this@SealedSessionFromHubTest)

        val hub = sealedHubFactory.create(upstreamFlow)
        subject = hub.createSession()
      }

  protected override fun subject() = subject

  protected override suspend fun emitUpstream(value: String) {
    upstreamFlow.emit(value)
  }

  protected override suspend fun createValue() = "${valueCounter.getAndIncrement()}"

  protected override suspend fun taskBarrier() = taskBarrier

  protected override fun testDispatcher(): CoroutineDispatcher = ioDispatcher

  @SealantScope
  @Component(
      modules = [SealedHubModule::class],
      dependencies =
          [
              ResourceSetComponent::class,
              RealisticCoroutinesTestingComponent::class,
              StandardObservableClosableComponent::class])
  interface TestComponent {
    fun inject(target: SealedSessionFromHubTest)
  }
}
