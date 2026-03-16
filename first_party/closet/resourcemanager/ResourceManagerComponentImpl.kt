package com.jackbradshaw.closet.resourcemanager

import com.jackbradshaw.coroutines.CoroutinesComponent
import dagger.Component
import com.jackbradshaw.closet.ClosetScope

/** [ResourceManagerComponent] backed by Dagger. */
@ClosetScope
@Component(
  dependencies = [CoroutinesComponent::class],
  modules = [ResourceManagerImplModule::class]
)
interface ResourceManagerComponentImpl : ResourceManagerComponent {
  @Component.Builder
  interface Builder {
    fun coroutines(coroutines: CoroutinesComponent): Builder
    fun build(): ResourceManagerComponentImpl
  }
}

/** Creates a new instance of [ResourceManagerComponent]. */
fun managerComponent(
  coroutines: CoroutinesComponent
): ResourceManagerComponent =
  DaggerResourceManagerComponentImpl.builder()
    .coroutines(coroutines)
    .build()
