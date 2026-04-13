package com.jackbradshaw.backstab.ksp.tests.topologies

import com.jackbradshaw.backstab.core.annotations.AggregateScope
import com.jackbradshaw.backstab.core.annotations.Backstab
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Inject

class TriangleFooC @Inject constructor()

class TriangleFooB @Inject constructor(val c: TriangleFooC)

class TriangleFooA @Inject constructor(val b: TriangleFooB, val c: TriangleFooC)

@Module
object TriangleCModule {
  val instance = TriangleFooC()

  @Provides fun provide(): TriangleFooC = instance
}

@Module
object TriangleBModule {
  val instance = TriangleFooB(TriangleCModule.instance)

  @Provides fun provide(): TriangleFooB = instance
}

@Module
object TriangleAModule {
  val instance = TriangleFooA(TriangleBModule.instance, TriangleCModule.instance)

  @Provides fun provide(): TriangleFooA = instance
}

@Component(modules = [TriangleCModule::class])
@Backstab
interface TriangleC {
  fun fooC(): TriangleFooC

  @Component.Builder
  interface Builder {
    fun build(): TriangleC
  }
}

@Component(dependencies = [TriangleC::class], modules = [TriangleBModule::class])
@Backstab
interface TriangleB {
  fun fooB(): TriangleFooB

  @Component.Builder
  interface Builder {
    fun build(): TriangleB

    fun c(n: TriangleC): Builder
  }
}

@Component(dependencies = [TriangleB::class, TriangleC::class], modules = [TriangleAModule::class])
@Backstab
interface TriangleA {
  fun fooA(): TriangleFooA

  @Component.Builder
  interface Builder {
    fun build(): TriangleA

    fun b(n: TriangleB): Builder

    fun c(n: TriangleC): Builder
  }
}

@Component(
    modules =
        [
            TriangleA_BackstabModule::class,
            TriangleB_BackstabModule::class,
            TriangleC_BackstabModule::class])
@com.jackbradshaw.backstab.core.annotations.AggregateScope
interface TriangleAgg {
  fun targetA(): TriangleA

  fun targetB(): TriangleB

  fun targetC(): TriangleC

  @Component.Builder
  interface Builder {
    fun build(): TriangleAgg
  }
}
