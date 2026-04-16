package com.jackbradshaw.backstab.ksp.tests.topologies

import com.jackbradshaw.backstab.core.annotations.AggregateScope
import com.jackbradshaw.backstab.core.annotations.Backstab
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Inject

class ShallowFooB @Inject constructor()

class ShallowFooA @Inject constructor(val b: ShallowFooB)

@Module
object ShallowBModule {
  val instance = ShallowFooB()

  @Provides fun provide(): ShallowFooB = instance
}

@Module
object ShallowAModule {
  val instance = ShallowFooA(ShallowBModule.instance)

  @Provides fun provide(): ShallowFooA = instance
}

@Component(modules = [ShallowAModule::class])
@Backstab
interface ShallowA {
  fun fooA(): ShallowFooA

  @Component.Builder
  interface Builder {
    fun build(): ShallowA
  }
}

@Component(modules = [ShallowBModule::class])
@Backstab
interface ShallowB {
  fun fooB(): ShallowFooB

  @Component.Builder
  interface Builder {
    fun build(): ShallowB
  }
}

@Component(modules = [ShallowA_BackstabModule::class, ShallowB_BackstabModule::class])
@com.jackbradshaw.backstab.core.annotations.AggregateScope
interface ShallowAgg {
  fun targetA(): ShallowA

  fun targetB(): ShallowB

  @Component.Builder
  interface Builder {
    fun build(): ShallowAgg
  }
}
