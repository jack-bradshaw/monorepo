package com.jackbradshaw.concurrency.quinn.testing

import com.jackbradshaw.chronosphere.testingtaskbarrier.TestingTaskBarrier
import com.jackbradshaw.chronosphere.testingtaskbarrier.TestingTaskBarrierComponent
import com.jackbradshaw.chronosphere.testingtaskbarrier.testingTaskBarrierComponent
import com.jackbradshaw.closet.observable.standard.StandardObservableClosableComponent
import com.jackbradshaw.closet.observable.standard.standardObservableClosableComponent
import com.jackbradshaw.closet.resourcemanager.set.ResourceSetComponent
import com.jackbradshaw.closet.resourcemanager.set.resourceSetComponent
import com.jackbradshaw.concurrency.quinn.Quinn
import com.jackbradshaw.concurrency.quinn.QuinnComponent
import com.jackbradshaw.concurrency.quinn.QuinnProductionModule
import com.jackbradshaw.concurrency.quinn.QuinnQualifier
import com.jackbradshaw.concurrency.quinn.QuinnScope
import com.jackbradshaw.concurrency.quinn.testing.factory.IdleableQuinnHubModule
import com.jackbradshaw.concurrency.quinn.testing.idleable.IdleableQuinn
import com.jackbradshaw.concurrency.quinn.testing.taskbarrier.TestingTaskBarrierModule
import com.jackbradshaw.coroutines.testing.realistic.realisticCoroutinesTestingComponent
import dagger.Component

@QuinnScope
@Component(
    dependencies =
        [
            TestingTaskBarrierComponent::class,
            ResourceSetComponent::class,
            StandardObservableClosableComponent::class],
    modules =
        [
            IdleableQuinnHubModule::class,
            TestingTaskBarrierModule::class,
            QuinnProductionModule::class])
interface TestingQuinnComponent : QuinnComponent {

  fun idleableHub(): IdleableQuinn.Hub

  fun factory(): Quinn.Factory

  @QuinnQualifier fun taskBarrier(): TestingTaskBarrier
}

fun testingQuinnComponent(): TestingQuinnComponent {
  val standard = standardObservableClosableComponent()
  val coroutines = realisticCoroutinesTestingComponent()
  return DaggerTestingQuinnComponent.builder()
      .testingTaskBarrierComponent(testingTaskBarrierComponent())
      .resourceSetComponent(resourceSetComponent(coroutines, standard))
      .standardObservableClosableComponent(standard)
      .build()
}
