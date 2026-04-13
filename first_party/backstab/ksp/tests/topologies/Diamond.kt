package com.jackbradshaw.backstab.ksp.tests.topologies

import com.jackbradshaw.backstab.core.annotations.AggregateScope
import com.jackbradshaw.backstab.core.annotations.Backstab
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Inject

class DiamondFooD @Inject constructor()

class DiamondFooB @Inject constructor(val d: DiamondFooD)

class DiamondFooC @Inject constructor(val d: DiamondFooD)

class DiamondFooA @Inject constructor(val b: DiamondFooB, val c: DiamondFooC)

@Module
object DiamondDModule {
  val instance = DiamondFooD()

  @Provides fun provide() = instance
}

@Module
object DiamondBModule {
  val instance = DiamondFooB(DiamondDModule.instance)

  @Provides fun provide() = instance
}

@Module
object DiamondCModule {
  val instance = DiamondFooC(DiamondDModule.instance)

  @Provides fun provide() = instance
}

@Module
object DiamondAModule {
  val instance = DiamondFooA(DiamondBModule.instance, DiamondCModule.instance)

  @Provides fun provide() = instance
}

@Component(modules = [DiamondAModule::class])
@Backstab
interface DiamondA {
  fun fooA(): DiamondFooA

  @Component.Builder
  interface Builder {
    fun build(): DiamondA
  }
}

@Component(modules = [DiamondBModule::class])
@Backstab
interface DiamondB {
  fun fooB(): DiamondFooB

  @Component.Builder
  interface Builder {
    fun build(): DiamondB
  }
}

@Component(modules = [DiamondCModule::class])
@Backstab
interface DiamondC {
  fun fooC(): DiamondFooC

  @Component.Builder
  interface Builder {
    fun build(): DiamondC
  }
}

@Component(modules = [DiamondDModule::class])
@Backstab
interface DiamondD {
  fun fooD(): DiamondFooD

  @Component.Builder
  interface Builder {
    fun build(): DiamondD
  }
}

@Component(
    modules =
        [
            DiamondA_BackstabModule::class,
            DiamondB_BackstabModule::class,
            DiamondC_BackstabModule::class,
            DiamondD_BackstabModule::class])
@com.jackbradshaw.backstab.core.annotations.AggregateScope
interface DiamondAgg {
  fun targetA(): DiamondA

  fun targetB(): DiamondB

  fun targetC(): DiamondC

  fun targetD(): DiamondD

  @Component.Builder
  interface Builder {
    fun build(): DiamondAgg
  }
}
