package com.jackbradshaw.backstab.oksp.tests.instantiators

import com.jackbradshaw.backstab.core.annotations.AggregateScope
import com.jackbradshaw.backstab.core.annotations.Backstab
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Inject

class FactoryFoo @Inject constructor()

@Module
object FactoryModule {
  val instance = FactoryFoo()

  @Provides fun provide(): FactoryFoo = instance
}

@Component(modules = [FactoryModule::class])
@Backstab
interface FactoryA {
  fun foo(): FactoryFoo

  @Component.Factory
  interface Factory {
    fun create(): FactoryA
  }
}

