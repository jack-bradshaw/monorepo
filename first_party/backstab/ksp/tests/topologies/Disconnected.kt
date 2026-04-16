package com.jackbradshaw.backstab.ksp.tests.topologies

import com.jackbradshaw.backstab.core.annotations.AggregateScope
import com.jackbradshaw.backstab.core.annotations.Backstab
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Inject

class DisconnectedFooA @Inject constructor()

class DisconnectedFooB @Inject constructor()

@Module
object DisconnectedAModule {
  val instance = DisconnectedFooA()

  @Provides fun provide() = instance
}

@Module
object DisconnectedBModule {
  val instance = DisconnectedFooB()

  @Provides fun provide() = instance
}

@Component(modules = [DisconnectedAModule::class])
@Backstab
interface DisconnectedA {
  fun fooA(): DisconnectedFooA

  @Component.Builder
  interface Builder {
    fun build(): DisconnectedA
  }
}

@Component(modules = [DisconnectedBModule::class])
@Backstab
interface DisconnectedB {
  fun fooB(): DisconnectedFooB

  @Component.Builder
  interface Builder {
    fun build(): DisconnectedB
  }
}

@Component(modules = [DisconnectedA_BackstabModule::class, DisconnectedB_BackstabModule::class])
@com.jackbradshaw.backstab.core.annotations.AggregateScope
interface DisconnectedAgg {
  fun targetA(): DisconnectedA

  fun targetB(): DisconnectedB

  @Component.Builder
  interface Builder {
    fun build(): DisconnectedAgg
  }
}
