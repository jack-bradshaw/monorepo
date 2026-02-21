package com.jackbradshaw.backstab.ksp.tests.binding.internalization

import com.jackbradshaw.backstab.core.annotations.AggregateScope
import com.jackbradshaw.backstab.core.annotations.Backstab
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Inject

interface TypeArgumentsDeepBox<T>

class TypeArgumentsDeepBoxImpl<T> @Inject constructor() : TypeArgumentsDeepBox<T>

@Module
object TypeArgumentsDeepModule {
  val instance = TypeArgumentsDeepBoxImpl<List<String>>()

  @Provides fun provideBox(): TypeArgumentsDeepBox<List<String>> = instance
}

@Component
@Backstab
interface TypeArgumentsDeepA {
  fun box(): TypeArgumentsDeepBox<List<String>>

  @Component.Builder
  interface Builder {
    @BindsInstance fun box(box: TypeArgumentsDeepBox<List<String>>): Builder

    fun build(): TypeArgumentsDeepA
  }
}

@Component(modules = [TypeArgumentsDeepModule::class, TypeArgumentsDeepA_BackstabModule::class])
@com.jackbradshaw.backstab.core.annotations.AggregateScope
interface TypeArgumentsDeepAgg {
  fun target(): TypeArgumentsDeepA

  @Component.Builder
  interface Builder {
    fun build(): TypeArgumentsDeepAgg
  }
}
