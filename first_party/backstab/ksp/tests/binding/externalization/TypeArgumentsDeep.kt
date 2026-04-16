package com.jackbradshaw.backstab.ksp.tests.binding.externalization

import com.jackbradshaw.backstab.core.annotations.AggregateScope
import com.jackbradshaw.backstab.core.annotations.Backstab
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Inject

class TypeArgumentsDeepBox<T> @Inject constructor()

@Module
object TypeArgumentsDeepModule {
  val instance = TypeArgumentsDeepBox<List<String>>()

  @Provides fun provideBox(): TypeArgumentsDeepBox<List<String>> = instance
}

@Component(modules = [TypeArgumentsDeepModule::class])
@Backstab
interface TypeArgumentsDeepA {
  fun box(): TypeArgumentsDeepBox<List<String>>

  @Component.Builder
  interface Builder {
    fun build(): TypeArgumentsDeepA
  }
}

@Component(modules = [TypeArgumentsDeepA_BackstabModule::class])
@com.jackbradshaw.backstab.core.annotations.AggregateScope
interface TypeArgumentsDeepAgg {
  fun target(): TypeArgumentsDeepA

  @Component.Builder
  interface Builder {
    fun build(): TypeArgumentsDeepAgg
  }
}
