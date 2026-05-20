package com.jackbradshaw.sealant.source

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
import com.jackbradshaw.sealant.hub.SealedHub
import com.jackbradshaw.sealant.hub.SealedHubModule
import com.jackbradshaw.sealant.hub.SealedHubTest
import dagger.Component
import java.util.concurrent.atomic.AtomicInteger
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class SealedSourceImplAsHubTest : SealedHubTest<String>() {

  private val nextValue = AtomicInteger(0)

  private lateinit var subject: SealedSourceImpl<String>

  @Inject lateinit var sourceFactory: SealedSourceImpl.Factory

  @Inject @Io lateinit var ioDispatcher: CoroutineDispatcher

  @Inject @Coroutines lateinit var taskBarrier: TestingTaskBarrier

  @Before
  fun setUp() =
      runBlocking<Unit> {
        val coroutines = realisticCoroutinesTestingComponent()
        val closables = standardObservableClosableComponent()
        DaggerSealedSourceImplAsHubTest_TestComponent.builder()
            .resourceSetComponent(resourceSetComponent(coroutines, closables))
            .realisticCoroutinesTestingComponent(coroutines)
            .standardObservableClosableComponent(closables)
            .build()
            .inject(this@SealedSourceImplAsHubTest)

        subject = sourceFactory.create<String>() as SealedSourceImpl<String>
      }

  override suspend fun subject(): SealedHub<String> = subject

  override suspend fun createValue(): String = nextValue.incrementAndGet().toString()

  override suspend fun emitUpstream(value: String) {
    subject.emit(value)
  }

  override fun testDispatcher(): CoroutineDispatcher = ioDispatcher

  override suspend fun taskBarrier(): TestingTaskBarrier = taskBarrier

  @SealantScope
  @Component(
      modules = [SealedHubModule::class],
      dependencies =
          [
              ResourceSetComponent::class,
              RealisticCoroutinesTestingComponent::class,
              StandardObservableClosableComponent::class])
  interface TestComponent {
    fun inject(target: SealedSourceImplAsHubTest)
  }
}
