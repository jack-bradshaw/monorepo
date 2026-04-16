package com.jackbradshaw.backstab.ksp.tests.topologies

import com.jackbradshaw.backstab.core.annotations.AggregateScope
import com.jackbradshaw.backstab.core.annotations.Backstab
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Inject

class ConvergingChainsFooG @Inject constructor()

class ConvergingChainsFooE @Inject constructor(val g: ConvergingChainsFooG)

class ConvergingChainsFooF @Inject constructor(val g: ConvergingChainsFooG)

class ConvergingChainsFooC @Inject constructor(val e: ConvergingChainsFooE)

class ConvergingChainsFooD @Inject constructor(val f: ConvergingChainsFooF)

class ConvergingChainsFooA @Inject constructor(val c: ConvergingChainsFooC)

class ConvergingChainsFooB @Inject constructor(val d: ConvergingChainsFooD)

@Module
object ConvergingChainsGModule {
  val instance = ConvergingChainsFooG()

  @Provides fun provide() = instance
}

@Module
object ConvergingChainsEModule {
  val instance = ConvergingChainsFooE(ConvergingChainsGModule.instance)

  @Provides fun provide() = instance
}

@Module
object ConvergingChainsFModule {
  val instance = ConvergingChainsFooF(ConvergingChainsGModule.instance)

  @Provides fun provide() = instance
}

@Module
object ConvergingChainsCModule {
  val instance = ConvergingChainsFooC(ConvergingChainsEModule.instance)

  @Provides fun provide() = instance
}

@Module
object ConvergingChainsDModule {
  val instance = ConvergingChainsFooD(ConvergingChainsFModule.instance)

  @Provides fun provide() = instance
}

@Module
object ConvergingChainsAModule {
  val instance = ConvergingChainsFooA(ConvergingChainsCModule.instance)

  @Provides fun provide() = instance
}

@Module
object ConvergingChainsBModule {
  val instance = ConvergingChainsFooB(ConvergingChainsDModule.instance)

  @Provides fun provide() = instance
}

@Component(modules = [ConvergingChainsAModule::class])
@Backstab
interface ConvergingChainsA {
  fun fooA(): ConvergingChainsFooA

  @Component.Builder
  interface Builder {
    fun build(): ConvergingChainsA
  }
}

@Component(modules = [ConvergingChainsBModule::class])
@Backstab
interface ConvergingChainsB {
  fun fooB(): ConvergingChainsFooB

  @Component.Builder
  interface Builder {
    fun build(): ConvergingChainsB
  }
}

@Component(modules = [ConvergingChainsCModule::class])
@Backstab
interface ConvergingChainsC {
  fun fooC(): ConvergingChainsFooC

  @Component.Builder
  interface Builder {
    fun build(): ConvergingChainsC
  }
}

@Component(modules = [ConvergingChainsDModule::class])
@Backstab
interface ConvergingChainsD {
  fun fooD(): ConvergingChainsFooD

  @Component.Builder
  interface Builder {
    fun build(): ConvergingChainsD
  }
}

@Component(modules = [ConvergingChainsEModule::class])
@Backstab
interface ConvergingChainsE {
  fun fooE(): ConvergingChainsFooE

  @Component.Builder
  interface Builder {
    fun build(): ConvergingChainsE
  }
}

@Component(modules = [ConvergingChainsFModule::class])
@Backstab
interface ConvergingChainsF {
  fun fooF(): ConvergingChainsFooF

  @Component.Builder
  interface Builder {
    fun build(): ConvergingChainsF
  }
}

@Component(modules = [ConvergingChainsGModule::class])
@Backstab
interface ConvergingChainsG {
  fun fooG(): ConvergingChainsFooG

  @Component.Builder
  interface Builder {
    fun build(): ConvergingChainsG
  }
}

@Component(
    modules =
        [
            ConvergingChainsA_BackstabModule::class,
            ConvergingChainsB_BackstabModule::class,
            ConvergingChainsC_BackstabModule::class,
            ConvergingChainsD_BackstabModule::class,
            ConvergingChainsE_BackstabModule::class,
            ConvergingChainsF_BackstabModule::class,
            ConvergingChainsG_BackstabModule::class])
@com.jackbradshaw.backstab.core.annotations.AggregateScope
interface ConvergingChainsAgg {
  fun targetA(): ConvergingChainsA

  fun targetB(): ConvergingChainsB

  fun targetC(): ConvergingChainsC

  @Component.Builder
  interface Builder {
    fun build(): ConvergingChainsAgg
  }
}
