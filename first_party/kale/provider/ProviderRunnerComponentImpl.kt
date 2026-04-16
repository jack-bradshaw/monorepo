package com.jackbradshaw.kale.provider

import com.jackbradshaw.kale.KaleScope
import dagger.Component

/** [ProviderRunnerComponent] backed by real KSP execution. */
@KaleScope
@Component(modules = [ProviderRunnerModule::class])
interface ProviderRunnerComponentImpl : ProviderRunnerComponent

/** Provides a new [ProviderRunnerComponentImpl]. */
fun providerRunnerComponent(): ProviderRunnerComponent = DaggerProviderRunnerComponentImpl.create()
