package com.jackbradshaw.backstab.ksp.tests.topologies

import com.jackbradshaw.backstab.core.annotations.AggregateScope
import com.jackbradshaw.backstab.core.annotations.Backstab
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Inject

class IsolatedFooA @Inject constructor()

@Module
object IsolatedAModule {
  val instance = IsolatedFooA()

  @Provides fun provide(): IsolatedFooA = instance
}

@Component(modules = [IsolatedAModule::class])
@Backstab
interface IsolatedA {
  fun fooA(): IsolatedFooA

  @Component.Builder
  interface Builder {
    fun build(): IsolatedA
  }
}

@Component(modules = [IsolatedA_BackstabModule::class])
@com.jackbradshaw.backstab.core.annotations.AggregateScope
interface IsolatedAgg {
  fun target(): IsolatedA

  @Component.Builder
  interface Builder {
    fun build(): IsolatedAgg
  }
}
