package com.jackbradshaw.backstab.ksp.tests.exposures

import com.jackbradshaw.backstab.core.annotations.AggregateScope
import com.jackbradshaw.backstab.core.annotations.Backstab
import dagger.Component
import javax.inject.Inject

class RootComponentFoo @Inject constructor()

@Component
@Backstab
interface RootComponentC {
  @Component.Builder
  interface Builder {
    fun build(): RootComponentC
  }
}

@Component(dependencies = [RootComponentC::class])
@Backstab
interface RootComponentB {
  @Component.Builder
  interface Builder {
    fun build(): RootComponentB

    fun c(c: RootComponentC): Builder
  }
}

@Component(dependencies = [RootComponentB::class])
@Backstab
interface RootComponentA {
  fun foo(): RootComponentFoo

  @Component.Builder
  interface Builder {
    fun build(): RootComponentA

    fun b(b: RootComponentB): Builder
  }
}

@Component(
    modules =
        [
            RootComponentA_BackstabModule::class,
            RootComponentB_BackstabModule::class,
            RootComponentC_BackstabModule::class])
@com.jackbradshaw.backstab.core.annotations.AggregateScope
interface RootComponentAgg {
  fun a(): RootComponentA

  @Component.Builder
  interface Builder {
    fun build(): RootComponentAgg
  }
}
