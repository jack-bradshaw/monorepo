package com.jackbradshaw.backstab.ksp.tests.topologies

import com.jackbradshaw.backstab.core.annotations.AggregateScope
import com.jackbradshaw.backstab.core.annotations.Backstab
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Inject

class AsymmetricChainsFooA @Inject constructor()

class AsymmetricChainsFooB @Inject constructor()

@Module
object AsymmetricChainsAModule {
  val instance = AsymmetricChainsFooA()

  @Provides fun provide() = instance
}

@Module
object AsymmetricChainsBModule {
  val instance = AsymmetricChainsFooB()

  @Provides fun provide() = instance
}

@Component(modules = [AsymmetricChainsAModule::class])
@Backstab
interface AsymmetricChainsA10 {
  fun fooA(): AsymmetricChainsFooA

  @Component.Builder
  interface Builder {
    fun build(): AsymmetricChainsA10
  }
}

@Component(dependencies = [AsymmetricChainsA10::class])
@Backstab
interface AsymmetricChainsA9 {
  fun fooA(): AsymmetricChainsFooA

  @Component.Builder
  interface Builder {
    fun build(): AsymmetricChainsA9

    fun a10(n: AsymmetricChainsA10): Builder
  }
}

@Component(dependencies = [AsymmetricChainsA9::class])
@Backstab
interface AsymmetricChainsA8 {
  fun fooA(): AsymmetricChainsFooA

  @Component.Builder
  interface Builder {
    fun build(): AsymmetricChainsA8

    fun a9(n: AsymmetricChainsA9): Builder
  }
}

@Component(dependencies = [AsymmetricChainsA8::class])
@Backstab
interface AsymmetricChainsA7 {
  fun fooA(): AsymmetricChainsFooA

  @Component.Builder
  interface Builder {
    fun build(): AsymmetricChainsA7

    fun a8(n: AsymmetricChainsA8): Builder
  }
}

@Component(dependencies = [AsymmetricChainsA7::class])
@Backstab
interface AsymmetricChainsA6 {
  fun fooA(): AsymmetricChainsFooA

  @Component.Builder
  interface Builder {
    fun build(): AsymmetricChainsA6

    fun a7(n: AsymmetricChainsA7): Builder
  }
}

@Component(dependencies = [AsymmetricChainsA6::class])
@Backstab
interface AsymmetricChainsA5 {
  fun fooA(): AsymmetricChainsFooA

  @Component.Builder
  interface Builder {
    fun build(): AsymmetricChainsA5

    fun a6(n: AsymmetricChainsA6): Builder
  }
}

@Component(dependencies = [AsymmetricChainsA5::class])
@Backstab
interface AsymmetricChainsA4 {
  fun fooA(): AsymmetricChainsFooA

  @Component.Builder
  interface Builder {
    fun build(): AsymmetricChainsA4

    fun a5(n: AsymmetricChainsA5): Builder
  }
}

@Component(dependencies = [AsymmetricChainsA4::class])
@Backstab
interface AsymmetricChainsA3 {
  fun fooA(): AsymmetricChainsFooA

  @Component.Builder
  interface Builder {
    fun build(): AsymmetricChainsA3

    fun a4(n: AsymmetricChainsA4): Builder
  }
}

@Component(dependencies = [AsymmetricChainsA3::class])
@Backstab
interface AsymmetricChainsA2 {
  fun fooA(): AsymmetricChainsFooA

  @Component.Builder
  interface Builder {
    fun build(): AsymmetricChainsA2

    fun a3(n: AsymmetricChainsA3): Builder
  }
}

@Component(dependencies = [AsymmetricChainsA2::class])
@Backstab
interface AsymmetricChainsA1 {
  fun fooA(): AsymmetricChainsFooA

  @Component.Builder
  interface Builder {
    fun build(): AsymmetricChainsA1

    fun a2(n: AsymmetricChainsA2): Builder
  }
}

@Component(modules = [AsymmetricChainsBModule::class])
@Backstab
interface AsymmetricChainsB2 {
  fun fooB(): AsymmetricChainsFooB

  @Component.Builder
  interface Builder {
    fun build(): AsymmetricChainsB2
  }
}

@Component(dependencies = [AsymmetricChainsB2::class])
@Backstab
interface AsymmetricChainsB1 {
  fun fooB(): AsymmetricChainsFooB

  @Component.Builder
  interface Builder {
    fun build(): AsymmetricChainsB1

    fun b2(n: AsymmetricChainsB2): Builder
  }
}

@Component(
    modules =
        [
            AsymmetricChainsA1_BackstabModule::class,
            AsymmetricChainsA2_BackstabModule::class,
            AsymmetricChainsA3_BackstabModule::class,
            AsymmetricChainsA4_BackstabModule::class,
            AsymmetricChainsA5_BackstabModule::class,
            AsymmetricChainsA6_BackstabModule::class,
            AsymmetricChainsA7_BackstabModule::class,
            AsymmetricChainsA8_BackstabModule::class,
            AsymmetricChainsA9_BackstabModule::class,
            AsymmetricChainsA10_BackstabModule::class,
            AsymmetricChainsB1_BackstabModule::class,
            AsymmetricChainsB2_BackstabModule::class])
@com.jackbradshaw.backstab.core.annotations.AggregateScope
interface AsymmetricChainsAgg {
  fun targetA(): AsymmetricChainsA1

  fun targetB(): AsymmetricChainsB1

  @Component.Builder
  interface Builder {
    fun build(): AsymmetricChainsAgg
  }
}
