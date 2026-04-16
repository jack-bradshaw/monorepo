package com.jackbradshaw.kale.provider

import com.jackbradshaw.coroutines.CoroutinesComponent
import com.jackbradshaw.coroutines.coroutinesComponent
import com.jackbradshaw.kale.KaleScope
import com.jackbradshaw.kale.ksprunner.KspRunnerModule
import dagger.Component

@KaleScope
@Component(
    dependencies = [CoroutinesComponent::class],
    modules = [KspRunnerModule::class, ProviderChassisModule::class])
interface ProviderChassisComponentImpl : ProviderChassisComponent {
  @Component.Builder
  interface Builder {
    fun coroutines(coroutines: CoroutinesComponent): Builder

    fun build(): ProviderChassisComponentImpl
  }
}

fun providerChassisComponent(
    coroutines: CoroutinesComponent = coroutinesComponent()
): ProviderChassisComponent =
    DaggerProviderChassisComponentImpl.builder().coroutines(coroutines).build()
