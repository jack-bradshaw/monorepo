package com.jackbradshaw.backstab.ksp.tests.binding.internalization

import com.jackbradshaw.backstab.core.annotations.AggregateScope
import com.jackbradshaw.backstab.core.annotations.Backstab
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Inject

interface TypeArgumentsInvariantBox<T>

class TypeArgumentsInvariantBoxImpl<T> @Inject constructor() : TypeArgumentsInvariantBox<T>

@Module
object TypeArgumentsInvariantModule {
  val instance = TypeArgumentsInvariantBoxImpl<String>()

  @Provides fun provideBox(): TypeArgumentsInvariantBox<String> = instance
}

@Component
@Backstab
interface TypeArgumentsInvariantA {
  fun box(): TypeArgumentsInvariantBox<String>

  @Component.Builder
  interface Builder {
    @BindsInstance fun box(box: TypeArgumentsInvariantBox<String>): Builder

    fun build(): TypeArgumentsInvariantA
  }
}

@Component(
    modules = [TypeArgumentsInvariantModule::class, TypeArgumentsInvariantA_BackstabModule::class])
@com.jackbradshaw.backstab.core.annotations.AggregateScope
interface TypeArgumentsInvariantAgg {
  fun target(): TypeArgumentsInvariantA

  @Component.Builder
  interface Builder {
    fun build(): TypeArgumentsInvariantAgg
  }
}
