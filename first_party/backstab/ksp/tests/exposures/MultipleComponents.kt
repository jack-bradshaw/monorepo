package com.jackbradshaw.backstab.ksp.tests.exposures

import com.jackbradshaw.backstab.core.annotations.AggregateScope
import com.jackbradshaw.backstab.core.annotations.Backstab
import dagger.Component
import javax.inject.Inject

class MultipleComponentsFoo @Inject constructor()

@Component
@Backstab
interface MultipleComponentsC {
  fun foo(): MultipleComponentsFoo

  @Component.Builder
  interface Builder {
    fun build(): MultipleComponentsC
  }
}

@Component(dependencies = [MultipleComponentsC::class])
@Backstab
interface MultipleComponentsB {
  fun foo(): MultipleComponentsFoo

  @Component.Builder
  interface Builder {
    fun build(): MultipleComponentsB

    fun c(c: MultipleComponentsC): Builder
  }
}

@Component(dependencies = [MultipleComponentsB::class])
@Backstab
interface MultipleComponentsA {
  fun foo(): MultipleComponentsFoo

  @Component.Builder
  interface Builder {
    fun build(): MultipleComponentsA

    fun b(b: MultipleComponentsB): Builder
  }
}

@Component(
    modules =
        [
            MultipleComponentsA_BackstabModule::class,
            MultipleComponentsB_BackstabModule::class,
            MultipleComponentsC_BackstabModule::class])
@com.jackbradshaw.backstab.core.annotations.AggregateScope
interface MultipleComponentsAgg {
  fun a(): MultipleComponentsA

  fun b(): MultipleComponentsB

  fun c(): MultipleComponentsC

  @Component.Builder
  interface Builder {
    fun build(): MultipleComponentsAgg
  }
}
