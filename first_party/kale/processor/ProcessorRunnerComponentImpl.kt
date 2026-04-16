package com.jackbradshaw.kale.processor

import com.jackbradshaw.kale.KaleScope
import com.jackbradshaw.kale.provider.ProviderRunner
import com.jackbradshaw.kale.provider.ProviderRunnerComponent
import com.jackbradshaw.kale.provider.providerRunnerComponent
import dagger.Component

/** [ProcessorRunnerComponent] backed by a [ProviderRunner]. */
@KaleScope
@Component(
    dependencies = [ProviderRunnerComponent::class], modules = [ProcessorRunnerModule::class])
interface ProcessorRunnerComponentImpl : ProcessorRunnerComponent {
  @Component.Builder
  interface Builder {
    fun consuming(providerRunner: ProviderRunnerComponent): Builder

    fun build(): ProcessorRunnerComponentImpl
  }
}

/** Provides a new [ProcessorRunnerComponentImpl]. */
fun processorRunnerComponent(
    providerRunner: ProviderRunnerComponent = providerRunnerComponent()
): ProcessorRunnerComponent =
    DaggerProcessorRunnerComponentImpl.builder().consuming(providerRunner).build()
