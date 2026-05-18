package com.jackbradshaw.concurrency.quinn

import com.jackbradshaw.closet.observable.standard.StandardObservableClosableComponent
import com.jackbradshaw.closet.observable.standard.standardObservableClosableComponent
import dagger.Component

/** Default [QuinnComponent]. */
@QuinnScope
@Component(
    dependencies = [StandardObservableClosableComponent::class], modules = [QuinnModule::class])
interface QuinnComponentImpl : QuinnComponent {
  @Component.Builder
  interface Builder {
    fun consuming(standard: StandardObservableClosableComponent): Builder

    fun build(): QuinnComponentImpl
  }
}

/** Provides a new [QuinnComponent]. */
fun quinnComponent(
    standard: StandardObservableClosableComponent = standardObservableClosableComponent()
): QuinnComponent = DaggerQuinnComponentImpl.builder().consuming(standard).build()
