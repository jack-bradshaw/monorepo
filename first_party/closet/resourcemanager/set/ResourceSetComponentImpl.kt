package com.jackbradshaw.closet.resourcemanager.set

import com.jackbradshaw.closet.ClosetScope
import com.jackbradshaw.closet.observable.standard.StandardObservableClosableComponent
import com.jackbradshaw.coroutines.CoroutinesComponent
import dagger.Component

/** Default implementation of [ResourceSetComponent]. */
@ClosetScope
@Component(
    dependencies = [CoroutinesComponent::class, StandardObservableClosableComponent::class],
    modules = [ResourceSetImplModule::class])
interface ResourceSetComponentImpl : ResourceSetComponent {
  @Component.Builder
  interface Builder {
    fun consuming(coroutines: CoroutinesComponent): Builder

    fun consuming(standard: StandardObservableClosableComponent): Builder

    fun build(): ResourceSetComponentImpl
  }
}

/** Creates a new instance of [ResourceSetComponent]. */
fun resourceSetComponent(
    coroutines: CoroutinesComponent,
    standard: StandardObservableClosableComponent
): ResourceSetComponent =
    DaggerResourceSetComponentImpl.builder().consuming(coroutines).consuming(standard).build()
