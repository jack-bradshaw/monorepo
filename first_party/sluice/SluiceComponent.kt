package com.jackbradshaw.sluice

import dagger.Component
import com.jackbradshaw.coroutines.CoroutinesComponent

@SluiceScope
@Component(
    modules = [SluiceModule::class],
    dependencies = [CoroutinesComponent::class]
)
interface SluiceComponent {
  fun sluiceFactory(): SluiceFactory
}

fun sluiceComponent(): SluiceComponent =
    DaggerSluiceComponent.builder()
        .coroutinesComponent(com.jackbradshaw.coroutines.coroutinesComponent())
        .build()
