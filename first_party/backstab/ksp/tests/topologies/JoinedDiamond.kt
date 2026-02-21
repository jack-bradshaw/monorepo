package com.jackbradshaw.backstab.ksp.tests.topologies

import com.jackbradshaw.backstab.core.annotations.AggregateScope
import com.jackbradshaw.backstab.core.annotations.Backstab
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Inject

class JoinedDiamondFooA @Inject constructor(val c: JoinedDiamondFooC, val d: JoinedDiamondFooD)

class JoinedDiamondFooB @Inject constructor(val c: JoinedDiamondFooC, val d: JoinedDiamondFooD)

class JoinedDiamondFooC @Inject constructor(val e: JoinedDiamondFooE)

class JoinedDiamondFooD @Inject constructor(val e: JoinedDiamondFooE)

class JoinedDiamondFooE @Inject constructor()

class JoinedDiamondFooF @Inject constructor(val d: JoinedDiamondFooD, val e: JoinedDiamondFooE)

@Module
object JoinedDiamondEModule {
  val instance = JoinedDiamondFooE()

  @Provides fun provide() = instance
}

@Module
object JoinedDiamondDModule {
  val instance = JoinedDiamondFooD(JoinedDiamondEModule.instance)

  @Provides fun provide() = instance
}

@Module
object JoinedDiamondCModule {
  val instance = JoinedDiamondFooC(JoinedDiamondEModule.instance)

  @Provides fun provide() = instance
}

@Module
object JoinedDiamondAModule {
  val instance = JoinedDiamondFooA(JoinedDiamondCModule.instance, JoinedDiamondDModule.instance)

  @Provides fun provide() = instance
}

@Module
object JoinedDiamondBModule {
  val instance = JoinedDiamondFooB(JoinedDiamondCModule.instance, JoinedDiamondDModule.instance)

  @Provides fun provide() = instance
}

@Module
object JoinedDiamondFModule {
  val instance = JoinedDiamondFooF(JoinedDiamondDModule.instance, JoinedDiamondEModule.instance)

  @Provides fun provide() = instance
}

@Component(modules = [JoinedDiamondEModule::class])
@Backstab
interface JoinedDiamondE {
  fun fooE(): JoinedDiamondFooE

  @Component.Builder
  interface Builder {
    fun build(): JoinedDiamondE
  }
}

@Component(dependencies = [JoinedDiamondE::class], modules = [JoinedDiamondDModule::class])
@Backstab
interface JoinedDiamondD {
  fun fooD(): JoinedDiamondFooD

  @Component.Builder
  interface Builder {
    fun build(): JoinedDiamondD

    fun e(n: JoinedDiamondE): Builder
  }
}

@Component(dependencies = [JoinedDiamondE::class], modules = [JoinedDiamondCModule::class])
@Backstab
interface JoinedDiamondC {
  fun fooC(): JoinedDiamondFooC

  @Component.Builder
  interface Builder {
    fun build(): JoinedDiamondC

    fun e(n: JoinedDiamondE): Builder
  }
}

@Component(
    dependencies = [JoinedDiamondD::class, JoinedDiamondE::class],
    modules = [JoinedDiamondFModule::class])
@Backstab
interface JoinedDiamondF {
  fun fooF(): JoinedDiamondFooF

  @Component.Builder
  interface Builder {
    fun build(): JoinedDiamondF

    fun d(n: JoinedDiamondD): Builder

    fun e(n: JoinedDiamondE): Builder
  }
}

@Component(
    dependencies = [JoinedDiamondC::class, JoinedDiamondD::class],
    modules = [JoinedDiamondAModule::class])
@Backstab
interface JoinedDiamondA {
  fun fooA(): JoinedDiamondFooA

  @Component.Builder
  interface Builder {
    fun build(): JoinedDiamondA

    fun c(n: JoinedDiamondC): Builder

    fun d(n: JoinedDiamondD): Builder
  }
}

@Component(
    dependencies = [JoinedDiamondC::class, JoinedDiamondD::class],
    modules = [JoinedDiamondBModule::class])
@Backstab
interface JoinedDiamondB {
  fun fooB(): JoinedDiamondFooB

  @Component.Builder
  interface Builder {
    fun build(): JoinedDiamondB

    fun c(n: JoinedDiamondC): Builder

    fun d(n: JoinedDiamondD): Builder
  }
}

@Component(
    modules =
        [
            JoinedDiamondA_BackstabModule::class,
            JoinedDiamondB_BackstabModule::class,
            JoinedDiamondC_BackstabModule::class,
            JoinedDiamondD_BackstabModule::class,
            JoinedDiamondE_BackstabModule::class,
            JoinedDiamondF_BackstabModule::class])
@com.jackbradshaw.backstab.core.annotations.AggregateScope
interface JoinedDiamondAgg {
  fun targetA(): JoinedDiamondA

  fun targetB(): JoinedDiamondB

  fun targetC(): JoinedDiamondC

  fun targetD(): JoinedDiamondD

  fun targetE(): JoinedDiamondE

  fun targetF(): JoinedDiamondF

  @Component.Builder
  interface Builder {
    fun build(): JoinedDiamondAgg
  }
}
