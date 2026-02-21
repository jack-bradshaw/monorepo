package com.jackbradshaw.backstab.ksp.tests.binding.externalization

import com.jackbradshaw.backstab.core.annotations.AggregateScope
import com.jackbradshaw.backstab.core.annotations.Backstab
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Inject

class TypeArgumentsInvariantBox<T> @Inject constructor()

@Module
object TypeArgumentsInvariantModule {
  val instance = TypeArgumentsInvariantBox<String>()

  @Provides fun provideBox(): TypeArgumentsInvariantBox<String> = instance
}

@Component(modules = [TypeArgumentsInvariantModule::class])
@Backstab
interface TypeArgumentsInvariantA {
  fun box(): TypeArgumentsInvariantBox<String>

  @Component.Builder
  interface Builder {
    fun build(): TypeArgumentsInvariantA
  }
}

@Component(modules = [TypeArgumentsInvariantA_BackstabModule::class])
@com.jackbradshaw.backstab.core.annotations.AggregateScope
interface TypeArgumentsInvariantAgg {
  fun target(): TypeArgumentsInvariantA

  @Component.Builder
  interface Builder {
    fun build(): TypeArgumentsInvariantAgg
  }
}
