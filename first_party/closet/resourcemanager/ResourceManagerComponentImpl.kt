package com.jackbradshaw.closet.resourcemanager

import com.jackbradshaw.coroutines.CoroutinesComponent
import dagger.Component
import javax.inject.Scope

@Scope
annotation class ClosetScope

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
