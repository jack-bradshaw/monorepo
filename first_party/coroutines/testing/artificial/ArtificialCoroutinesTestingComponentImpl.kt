package com.jackbradshaw.coroutines.testing.artificial

import com.jackbradshaw.chronosphere.testingtaskdriver.TestingTaskDriverComponent
import com.jackbradshaw.chronosphere.testingtaskdriver.testingTaskDriverComponent
import com.jackbradshaw.coroutines.CoroutinesDaggerScope
import com.jackbradshaw.coroutines.testing.artificial.cpu.TestingCpuModule
import com.jackbradshaw.coroutines.testing.artificial.dispatcher.AdvancableDispatcherModule
import com.jackbradshaw.coroutines.testing.artificial.io.TestingIoModule
import com.jackbradshaw.coroutines.testing.artificial.testingtaskdriver.TestingTaskDriverModule
import dagger.Component

/** A [ArtificialCoroutinesTestingComponent] that delegates to controlled test dispatchers for execution. */
@CoroutinesDaggerScope
@Component(
    dependencies =
        [TestingTaskDriverComponent::class],
    modules =
        [
            TestingCpuModule::class,
            TestingIoModule::class,
            TestingTaskDriverModule::class,
            AdvancableDispatcherModule::class,
        ])
interface ArtificialCoroutinesTestingComponentImpl : ArtificialCoroutinesTestingComponent {
  @Component.Builder
  interface Builder {
    fun consuming(
        component: TestingTaskDriverComponent
    ): Builder

    fun build(): ArtificialCoroutinesTestingComponentImpl
  }
}

fun artificialCoroutinesTestingComponent(): ArtificialCoroutinesTestingComponent =
    DaggerArtificialCoroutinesTestingComponentImpl.builder()
        .consuming(testingTaskDriverComponent())
        .build()
