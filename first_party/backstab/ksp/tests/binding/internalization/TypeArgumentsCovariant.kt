package com.jackbradshaw.backstab.ksp.tests.binding.internalization

import com.jackbradshaw.backstab.core.annotations.AggregateScope
import com.jackbradshaw.backstab.core.annotations.Backstab
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Inject

interface TypeArgumentsCovariantFoo

class TypeArgumentsCovariantFooImpl @Inject constructor() : TypeArgumentsCovariantFoo

interface TypeArgumentsCovariantBox<T>

class TypeArgumentsCovariantBoxImpl<T> @Inject constructor() : TypeArgumentsCovariantBox<T>

@Module
object TypeArgumentsCovariantModule {
  val instance = TypeArgumentsCovariantBoxImpl<TypeArgumentsCovariantFooImpl>()

  @Provides fun provideOut(): TypeArgumentsCovariantBox<out TypeArgumentsCovariantFoo> = instance
}

@Component
@Backstab
interface TypeArgumentsCovariantA {
  fun box(): TypeArgumentsCovariantBox<out TypeArgumentsCovariantFoo>

  @Component.Builder
  interface Builder {
    @BindsInstance fun box(box: TypeArgumentsCovariantBox<out TypeArgumentsCovariantFoo>): Builder

    fun build(): TypeArgumentsCovariantA
  }
}

@Component(
    modules = [TypeArgumentsCovariantModule::class, TypeArgumentsCovariantA_BackstabModule::class])
@com.jackbradshaw.backstab.core.annotations.AggregateScope
interface TypeArgumentsCovariantAgg {
  fun target(): TypeArgumentsCovariantA

  @Component.Builder
  interface Builder {
    fun build(): TypeArgumentsCovariantAgg
  }
}
