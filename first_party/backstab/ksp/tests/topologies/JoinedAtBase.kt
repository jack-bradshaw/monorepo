package com.jackbradshaw.backstab.ksp.tests.topologies

import com.jackbradshaw.backstab.core.annotations.AggregateScope
import com.jackbradshaw.backstab.core.annotations.Backstab
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Inject

class JoinedAtBaseFooA @Inject constructor(val c: JoinedAtBaseFooC)

class JoinedAtBaseFooB @Inject constructor(val c: JoinedAtBaseFooC)

class JoinedAtBaseFooC @Inject constructor()

@Module
object JoinedAtBaseCModule {
  val instance = JoinedAtBaseFooC()

  @Provides fun provide() = instance
}

@Module
object JoinedAtBaseAModule {
  val instance = JoinedAtBaseFooA(JoinedAtBaseCModule.instance)

  @Provides fun provide() = instance
}

@Module
object JoinedAtBaseBModule {
  val instance = JoinedAtBaseFooB(JoinedAtBaseCModule.instance)

  @Provides fun provide() = instance
}

@Component(modules = [JoinedAtBaseCModule::class])
@Backstab
interface JoinedAtBaseC {
  fun fooC(): JoinedAtBaseFooC

  @Component.Builder
  interface Builder {
    fun build(): JoinedAtBaseC
  }
}

@Component(dependencies = [JoinedAtBaseC::class], modules = [JoinedAtBaseAModule::class])
@Backstab
interface JoinedAtBaseA {
  fun fooA(): JoinedAtBaseFooA

  @Component.Builder
  interface Builder {
    fun build(): JoinedAtBaseA

    fun c(n: JoinedAtBaseC): Builder
  }
}

@Component(dependencies = [JoinedAtBaseC::class], modules = [JoinedAtBaseBModule::class])
@Backstab
interface JoinedAtBaseB {
  fun fooB(): JoinedAtBaseFooB

  @Component.Builder
  interface Builder {
    fun build(): JoinedAtBaseB

    fun c(n: JoinedAtBaseC): Builder
  }
}

@Component(
    modules =
        [
            JoinedAtBaseA_BackstabModule::class,
            JoinedAtBaseB_BackstabModule::class,
            JoinedAtBaseC_BackstabModule::class])
@com.jackbradshaw.backstab.core.annotations.AggregateScope
interface JoinedAtBaseAgg {
  fun targetA(): JoinedAtBaseA

  fun targetB(): JoinedAtBaseB

  fun targetC(): JoinedAtBaseC

  @Component.Builder
  interface Builder {
    fun build(): JoinedAtBaseAgg
  }
}
