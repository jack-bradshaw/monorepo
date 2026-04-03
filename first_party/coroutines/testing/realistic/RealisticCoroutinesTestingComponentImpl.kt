package com.jackbradshaw.coroutines.testing.realistic

import com.jackbradshaw.chronosphere.testingtaskbarrier.TestingTaskBarrierComponent
import com.jackbradshaw.chronosphere.testingtaskbarrier.taskBarrierComponent
import com.jackbradshaw.coroutines.CoroutinesDaggerScope
import com.jackbradshaw.coroutines.testing.realistic.cpu.TestingCpuModule
import com.jackbradshaw.coroutines.testing.realistic.dispatcher.IdleableDispatcherModule
import com.jackbradshaw.coroutines.testing.realistic.io.TestingIoModule
import com.jackbradshaw.coroutines.testing.realistic.testingtaskbarrier.TestingTaskBarrierModule
import dagger.Component

/** A [RealisticCoroutinesTestingComponent] that delegates to a thread pool for execution. */
@CoroutinesDaggerScope
@Component(
    dependencies =
        [TestingTaskBarrierComponent::class],
    modules =
        [
            TestingCpuModule::class,
            TestingIoModule::class,
            TestingTaskBarrierModule::class,
            IdleableDispatcherModule::class,
        ])
interface RealisticCoroutinesTestingComponentImpl : RealisticCoroutinesTestingComponent {
  @Component.Builder
  interface Builder {
    fun consuming(
        component: TestingTaskBarrierComponent
    ): Builder

    fun build(): RealisticCoroutinesTestingComponentImpl
  }
}

fun realisticCoroutinesTestingComponent(): RealisticCoroutinesTestingComponent =
    DaggerRealisticCoroutinesTestingComponentImpl.builder()
        .consuming(taskBarrierComponent())
        .build()
