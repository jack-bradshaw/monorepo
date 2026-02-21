package com.jackbradshaw.backstab.ksp.tests.binding.internalization

import com.jackbradshaw.backstab.core.annotations.AggregateScope
import com.jackbradshaw.backstab.core.annotations.Backstab
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Inject

interface TypeArgumentsWideBox<T, U>

class TypeArgumentsWideBoxImpl<T, U> @Inject constructor() : TypeArgumentsWideBox<T, U>

@Module
object TypeArgumentsWideModule {
  val instance = TypeArgumentsWideBoxImpl<String, Int>()

  @Provides fun provideBox(): TypeArgumentsWideBox<String, Int> = instance
}

@Component
@Backstab
interface TypeArgumentsWideA {
  fun box(): TypeArgumentsWideBox<String, Int>

  @Component.Builder
  interface Builder {
    @BindsInstance fun box(box: TypeArgumentsWideBox<String, Int>): Builder

    fun build(): TypeArgumentsWideA
  }
}

@Component(modules = [TypeArgumentsWideModule::class, TypeArgumentsWideA_BackstabModule::class])
@com.jackbradshaw.backstab.core.annotations.AggregateScope
interface TypeArgumentsWideAgg {
  fun target(): TypeArgumentsWideA

  @Component.Builder
  interface Builder {
    fun build(): TypeArgumentsWideAgg
  }
}
