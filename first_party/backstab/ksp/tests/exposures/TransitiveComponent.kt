package com.jackbradshaw.backstab.ksp.tests.exposures

import com.jackbradshaw.backstab.core.annotations.AggregateScope
import com.jackbradshaw.backstab.core.annotations.Backstab
import dagger.Component
import javax.inject.Inject

class TransitiveComponentFoo @Inject constructor()

@Component
@Backstab
interface TransitiveComponentC {
  @Component.Builder
  interface Builder {
    fun build(): TransitiveComponentC
  }
}

@Component(dependencies = [TransitiveComponentC::class])
@Backstab
interface TransitiveComponentB {
  fun foo(): TransitiveComponentFoo

  @Component.Builder
  interface Builder {
    fun build(): TransitiveComponentB

    fun c(c: TransitiveComponentC): Builder
  }
}

@Component(dependencies = [TransitiveComponentB::class])
@Backstab
interface TransitiveComponentA {
  @Component.Builder
  interface Builder {
    fun build(): TransitiveComponentA

    fun b(b: TransitiveComponentB): Builder
  }
}

@Component(
    modules =
        [
            TransitiveComponentA_BackstabModule::class,
            TransitiveComponentB_BackstabModule::class,
            TransitiveComponentC_BackstabModule::class])
@com.jackbradshaw.backstab.core.annotations.AggregateScope
interface TransitiveComponentAgg {
  fun b(): TransitiveComponentB

  @Component.Builder
  interface Builder {
    fun build(): TransitiveComponentAgg
  }
}
