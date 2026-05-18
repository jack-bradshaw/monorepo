package com.jackbradshaw.closet.resourcemanager.map

import com.jackbradshaw.closet.ClosetScope
import com.jackbradshaw.closet.observable.standard.StandardObservableClosableComponent
import com.jackbradshaw.coroutines.CoroutinesComponent
import dagger.Component

/** Default implementation of [ResourceMapComponent]. */
@ClosetScope
@Component(
    dependencies = [CoroutinesComponent::class, StandardObservableClosableComponent::class],
    modules = [ResourceMapImplModule::class])
interface ResourceMapComponentImpl : ResourceMapComponent {
  @Component.Builder
  interface Builder {
    fun consuming(coroutines: CoroutinesComponent): Builder

    fun consuming(standard: StandardObservableClosableComponent): Builder

    fun build(): ResourceMapComponentImpl
  }
}

/** Creates a new instance of [ResourceMapComponent]. */
fun resourceMapComponent(
    coroutines: CoroutinesComponent,
    standard: StandardObservableClosableComponent
): ResourceMapComponent =
    DaggerResourceMapComponentImpl.builder().consuming(coroutines).consuming(standard).build()
