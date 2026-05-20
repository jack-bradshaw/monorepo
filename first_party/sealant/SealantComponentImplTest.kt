package com.jackbradshaw.sealant

import com.jackbradshaw.chronosphere.testingtaskbarrier.TestingTaskBarrier
import com.jackbradshaw.closet.observable.standard.StandardObservableClosableComponent
import com.jackbradshaw.closet.observable.standard.standardObservableClosableComponent
import com.jackbradshaw.closet.resourcemanager.set.ResourceSetComponent
import com.jackbradshaw.closet.resourcemanager.set.resourceSetComponent
import com.jackbradshaw.coroutines.Io
import com.jackbradshaw.coroutines.testing.Coroutines
import com.jackbradshaw.coroutines.testing.realistic.RealisticCoroutinesTestingComponent
import com.jackbradshaw.coroutines.testing.realistic.realisticCoroutinesTestingComponent
import com.jackbradshaw.sealant.hub.SealedHubModule
import com.jackbradshaw.sealant.source.SealedSourceModule
import dagger.Component
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import org.junit.Before
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class SealantComponentImplTest : SealantComponentTest() {

  private lateinit var component: TestComponent

  @Inject @Io lateinit var ioDispatcher: CoroutineDispatcher

  @Inject @Coroutines lateinit var barrier: TestingTaskBarrier

  @Before
  fun setUp() {
    val coroutines = realisticCoroutinesTestingComponent()
    val closables = standardObservableClosableComponent()
    component =
        DaggerSealantComponentImplTest_TestComponent.builder()
            .resourceSetComponent(resourceSetComponent(coroutines, closables))
            .realisticCoroutinesTestingComponent(coroutines)
            .standardObservableClosableComponent(closables)
            .build()
    component.inject(this)
  }

  override suspend fun subject(): SealantComponent = component

  override suspend fun barrier(): TestingTaskBarrier = barrier

  override fun testDispatcher(): CoroutineDispatcher = ioDispatcher

  @SealantScope
  @Component(
      modules = [SealedHubModule::class, SealedSourceModule::class],
      dependencies =
          [
              ResourceSetComponent::class,
              RealisticCoroutinesTestingComponent::class,
              StandardObservableClosableComponent::class])
  interface TestComponent : SealantComponent {
    fun inject(target: SealantComponentImplTest)
  }
}
