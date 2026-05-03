package com.jackbradshaw.backstab.oksp.tests.binding.externalization

import com.jackbradshaw.backstab.core.annotations.AggregateScope
import com.jackbradshaw.backstab.core.annotations.Backstab
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Inject

class TypeArgumentsWideBox<T, U> @Inject constructor()

@Module
object TypeArgumentsWideModule {
  val instance = TypeArgumentsWideBox<String, Int>()

  @Provides fun provideBox(): TypeArgumentsWideBox<String, Int> = instance
}

@Component(modules = [TypeArgumentsWideModule::class])
@Backstab
interface TypeArgumentsWideA {
  fun box(): TypeArgumentsWideBox<String, Int>

  @Component.Builder
  interface Builder {
    fun build(): TypeArgumentsWideA
  }
}

@Component(modules = [TypeArgumentsWideA_BackstabModule::class])
@AggregateScope
interface TypeArgumentsWideAgg {
  fun target(): TypeArgumentsWideA

  @Component.Builder
  interface Builder {
    fun build(): TypeArgumentsWideAgg
  }
}
