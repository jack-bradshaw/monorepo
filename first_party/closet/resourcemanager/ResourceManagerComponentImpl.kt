package com.jackbradshaw.closet.resourcemanager

import com.jackbradshaw.coroutines.CoroutinesComponent
import dagger.Component
import com.jackbradshaw.closet.ClosetScope

@ClosetScope
@Component(
  dependencies = [CoroutinesComponent::class],
  modules = [ResourceManagerModule::class]
)
interface ResourceManagerComponentImpl : ResourceManagerComponent {
  @Component.Builder
  interface Builder {
    fun coroutines(coroutines: CoroutinesComponent): Builder
    fun build(): ResourceManagerComponentImpl
  }
}

fun managerComponent(
  coroutines: CoroutinesComponent
): ResourceManagerComponent =
  DaggerResourceManagerComponentImpl.builder()
    .coroutines(coroutines)
    .build()
