package com.jackbradshaw.backstab.ksp.tests.binding.internalization

import com.jackbradshaw.backstab.core.annotations.AggregateScope
import com.jackbradshaw.backstab.core.annotations.Backstab
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Inject

interface TypeArgumentsContravariantFoo

interface TypeArgumentsContravariantBox<T>

class TypeArgumentsContravariantBoxImpl<T> @Inject constructor() : TypeArgumentsContravariantBox<T>

@Module
object TypeArgumentsContravariantModule {
  val instance = TypeArgumentsContravariantBoxImpl<Any>()

  @Provides
  fun provideIn(): TypeArgumentsContravariantBox<in TypeArgumentsContravariantFoo> = instance
}

@Component
@Backstab
interface TypeArgumentsContravariantA {
  fun box(): TypeArgumentsContravariantBox<in TypeArgumentsContravariantFoo>

  @Component.Builder
  interface Builder {
    @BindsInstance
    fun box(box: TypeArgumentsContravariantBox<in TypeArgumentsContravariantFoo>): Builder

    fun build(): TypeArgumentsContravariantA
  }
}

@Component(
    modules =
        [
            TypeArgumentsContravariantModule::class,
            TypeArgumentsContravariantA_BackstabModule::class])
@com.jackbradshaw.backstab.core.annotations.AggregateScope
interface TypeArgumentsContravariantAgg {
  fun target(): TypeArgumentsContravariantA

  @Component.Builder
  interface Builder {
    fun build(): TypeArgumentsContravariantAgg
  }
}
