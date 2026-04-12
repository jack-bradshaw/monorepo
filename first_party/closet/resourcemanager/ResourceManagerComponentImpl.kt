package com.jackbradshaw.closet.resourcemanager

import com.jackbradshaw.closet.ClosetScope
import com.jackbradshaw.coroutines.CoroutinesComponent
import dagger.Component

/** Default implementation of [ResourceManagerComponent]. */
@ClosetScope
@Component(
    dependencies = [CoroutinesComponent::class], modules = [ResourceManagerImplModule::class])
interface ResourceManagerComponentImpl : ResourceManagerComponent {
  @Component.Builder
  interface Builder {
    fun consuming(coroutines: CoroutinesComponent): Builder

    fun build(): ResourceManagerComponentImpl
  }
}

/** Creates a new instance of [ResourceManagerComponent]. */
fun resourceManagerComponent(coroutines: CoroutinesComponent): ResourceManagerComponent =
    DaggerResourceManagerComponentImpl.builder().consuming(coroutines).build()
