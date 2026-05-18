package com.jackbradshaw.closet.observable.standard

import com.jackbradshaw.closet.ClosetScope
import dagger.Binds
import dagger.Component
import dagger.Module

/** Default implementation of [ObservableClosableDelegateComponent]. */
@ClosetScope
@Component(modules = [StandardObservableClosableModule::class])
interface StandardObservableClosableComponentImpl : StandardObservableClosableComponent {
  @Component.Builder
  interface Builder {
    fun build(): StandardObservableClosableComponentImpl
  }
}

/**
 * Creates a new [StandardObservableClosableComponent]. Calls are not idempotent and always return a
 * new instance.
 */
fun standardObservableClosableComponent(): StandardObservableClosableComponent =
    DaggerStandardObservableClosableComponentImpl.builder().build()

@Module
internal abstract class StandardObservableClosableModule {
  @Binds
  @ClosetScope
  abstract fun bindFactory(
      impl: StandardObservableClosableFactoryImpl
  ): StandardObservableClosableFactory
}
