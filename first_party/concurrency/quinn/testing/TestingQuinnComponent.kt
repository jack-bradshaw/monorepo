package com.jackbradshaw.concurrency.quinn.testing

import com.jackbradshaw.chronosphere.testingtaskbarrier.TestingTaskBarrier
import com.jackbradshaw.chronosphere.testingtaskbarrier.TestingTaskBarrierComponent
import com.jackbradshaw.closet.resourcemanager.ResourceManagerComponent

import com.jackbradshaw.concurrency.quinn.Quinn
import com.jackbradshaw.concurrency.quinn.QuinnComponent
import com.jackbradshaw.concurrency.quinn.QuinnProductionModule
import com.jackbradshaw.concurrency.quinn.QuinnQualifier
import com.jackbradshaw.concurrency.quinn.testing.factory.IdleableQuinnHubModule
import com.jackbradshaw.concurrency.quinn.testing.idleable.IdleableQuinn
import com.jackbradshaw.concurrency.quinn.testing.taskbarrier.TestingTaskBarrierModule
import dagger.Component

import javax.inject.Singleton

@Singleton
@Component(

    dependencies = [TestingTaskBarrierComponent::class, ResourceManagerComponent::class],
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
