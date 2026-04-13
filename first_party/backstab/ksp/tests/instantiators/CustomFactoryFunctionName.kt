package com.jackbradshaw.backstab.ksp.tests.instantiators

import com.jackbradshaw.backstab.core.annotations.AggregateScope
import com.jackbradshaw.backstab.core.annotations.Backstab
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Inject

class CustomFactoryFoo @Inject constructor()

@Module
object CustomFactoryModule {
  val instance = CustomFactoryFoo()

  @Provides fun provide(): CustomFactoryFoo = instance
}

@Component(modules = [CustomFactoryModule::class])
@Backstab
interface CustomFactoryA {
  fun foo(): CustomFactoryFoo

  @Component.Factory
  interface Factory {
    fun execute(): CustomFactoryA
  }
}

@Component(modules = [CustomFactoryA_BackstabModule::class])
@com.jackbradshaw.backstab.core.annotations.AggregateScope
interface CustomFactoryAgg {
  fun target(): CustomFactoryA

  @Component.Factory
  interface Factory {
    fun execute(): CustomFactoryAgg
  }
}
