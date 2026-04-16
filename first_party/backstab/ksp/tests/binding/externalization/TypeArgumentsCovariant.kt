package com.jackbradshaw.backstab.ksp.tests.binding.externalization

import com.jackbradshaw.backstab.core.annotations.AggregateScope
import com.jackbradshaw.backstab.core.annotations.Backstab
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Inject

class TypeArgumentsCovariantFoo @Inject constructor()

class TypeArgumentsCovariantBox<T> @Inject constructor()

@Module
object TypeArgumentsCovariantModule {
  val instance = TypeArgumentsCovariantBox<TypeArgumentsCovariantFoo>()

  @Provides fun provideBox(): TypeArgumentsCovariantBox<out TypeArgumentsCovariantFoo> = instance
}

@Component(modules = [TypeArgumentsCovariantModule::class])
@Backstab
interface TypeArgumentsCovariantA {
  fun box(): TypeArgumentsCovariantBox<out TypeArgumentsCovariantFoo>

  @Component.Builder
  interface Builder {
    fun build(): TypeArgumentsCovariantA
  }
}

@Component(modules = [TypeArgumentsCovariantA_BackstabModule::class])
@com.jackbradshaw.backstab.core.annotations.AggregateScope
interface TypeArgumentsCovariantAgg {
  fun target(): TypeArgumentsCovariantA

  @Component.Builder
  interface Builder {
    fun build(): TypeArgumentsCovariantAgg
  }
}
