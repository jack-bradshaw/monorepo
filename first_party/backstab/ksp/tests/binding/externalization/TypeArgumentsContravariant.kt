package com.jackbradshaw.backstab.ksp.tests.binding.externalization

import com.jackbradshaw.backstab.core.annotations.AggregateScope
import com.jackbradshaw.backstab.core.annotations.Backstab
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Inject

class TypeArgumentsContravariantFoo @Inject constructor()

class TypeArgumentsContravariantBox<T> @Inject constructor()

@Module
object TypeArgumentsContravariantModule {
  val instance = TypeArgumentsContravariantBox<Any>()

  @Provides
  fun provideBox(): TypeArgumentsContravariantBox<in TypeArgumentsContravariantFoo> = instance
}

@Component(modules = [TypeArgumentsContravariantModule::class])
@Backstab
interface TypeArgumentsContravariantA {
  fun box(): TypeArgumentsContravariantBox<in TypeArgumentsContravariantFoo>

  @Component.Builder
  interface Builder {
    fun build(): TypeArgumentsContravariantA
  }
}

@Component(modules = [TypeArgumentsContravariantA_BackstabModule::class])
@com.jackbradshaw.backstab.core.annotations.AggregateScope
interface TypeArgumentsContravariantAgg {
  fun target(): TypeArgumentsContravariantA

  @Component.Builder
  interface Builder {
    fun build(): TypeArgumentsContravariantAgg
  }
}
