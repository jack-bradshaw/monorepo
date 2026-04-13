package com.jackbradshaw.backstab.ksp.tests.topologies

import com.jackbradshaw.backstab.core.annotations.AggregateScope
import com.jackbradshaw.backstab.core.annotations.Backstab
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Inject

class DeepFooE @Inject constructor()

class DeepFooD @Inject constructor(val e: DeepFooE)

class DeepFooC @Inject constructor(val d: DeepFooD)

class DeepFooB @Inject constructor(val c: DeepFooC)

class DeepFooA @Inject constructor(val b: DeepFooB)

@Module
object DeepEModule {
  val instance = DeepFooE()

  @Provides fun provide() = instance
}

@Module
object DeepDModule {
  val instance = DeepFooD(DeepEModule.instance)

  @Provides fun provide() = instance
}

@Module
object DeepCModule {
  val instance = DeepFooC(DeepDModule.instance)

  @Provides fun provide() = instance
}

@Module
object DeepBModule {
  val instance = DeepFooB(DeepCModule.instance)

  @Provides fun provide() = instance
}

@Module
object DeepAModule {
  val instance = DeepFooA(DeepBModule.instance)

  @Provides fun provide() = instance
}

@Component(modules = [DeepAModule::class])
@Backstab
interface DeepA {
  fun fooA(): DeepFooA

  @Component.Builder
  interface Builder {
    fun build(): DeepA
  }
}

@Component(modules = [DeepBModule::class])
@Backstab
interface DeepB {
  fun fooB(): DeepFooB

  @Component.Builder
  interface Builder {
    fun build(): DeepB
  }
}

@Component(modules = [DeepCModule::class])
@Backstab
interface DeepC {
  fun fooC(): DeepFooC

  @Component.Builder
  interface Builder {
    fun build(): DeepC
  }
}

@Component(modules = [DeepDModule::class])
@Backstab
interface DeepD {
  fun fooD(): DeepFooD

  @Component.Builder
  interface Builder {
    fun build(): DeepD
  }
}

@Component(modules = [DeepEModule::class])
@Backstab
interface DeepE {
  fun fooE(): DeepFooE

  @Component.Builder
  interface Builder {
    fun build(): DeepE
  }
}

@Component(
    modules =
        [
            DeepA_BackstabModule::class,
            DeepB_BackstabModule::class,
            DeepC_BackstabModule::class,
            DeepD_BackstabModule::class,
            DeepE_BackstabModule::class])
@com.jackbradshaw.backstab.core.annotations.AggregateScope
interface DeepAgg {
  fun targetA(): DeepA

  fun targetB(): DeepB

  fun targetC(): DeepC

  fun targetD(): DeepD

  fun targetE(): DeepE

  @Component.Builder
  interface Builder {
    fun build(): DeepAgg
  }
}
