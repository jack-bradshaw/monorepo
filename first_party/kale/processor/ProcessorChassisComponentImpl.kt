package com.jackbradshaw.kale.processor

import com.jackbradshaw.coroutines.CoroutinesComponent
import com.jackbradshaw.coroutines.coroutinesComponent
import com.jackbradshaw.kale.KaleScope
import com.jackbradshaw.kale.ksprunner.KspRunnerModule
import dagger.Component

@KaleScope
@Component(
    dependencies = [CoroutinesComponent::class],
    modules = [KspRunnerModule::class, ProcessorChassisModule::class])
interface ProcessorChassisComponentImpl : ProcessorChassisComponent {
  @Component.Builder
  interface Builder {
    fun coroutines(coroutines: CoroutinesComponent): Builder

    fun build(): ProcessorChassisComponentImpl
  }
}

fun processorChassisComponent(
    coroutines: CoroutinesComponent = coroutinesComponent()
): ProcessorChassisComponent =
    DaggerProcessorChassisComponentImpl.builder().coroutines(coroutines).build()
