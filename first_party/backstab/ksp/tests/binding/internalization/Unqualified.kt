package com.jackbradshaw.backstab.ksp.tests.binding.internalization

import com.jackbradshaw.backstab.core.annotations.AggregateScope
import com.jackbradshaw.backstab.core.annotations.Backstab
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Inject

interface UnqualifiedFoo

class UnqualifiedFooImpl @Inject constructor() : UnqualifiedFoo

@Module
object UnqualifiedModule {
  val instance = UnqualifiedFooImpl()

  @Provides fun provideFoo(): UnqualifiedFoo = instance
}

@Component
@Backstab
interface UnqualifiedA {
  fun foo(): UnqualifiedFoo

  @Component.Builder
  interface Builder {
    @BindsInstance fun foo(foo: UnqualifiedFoo): Builder

    fun build(): UnqualifiedA
  }
}

@Component(modules = [UnqualifiedModule::class, UnqualifiedA_BackstabModule::class])
@com.jackbradshaw.backstab.core.annotations.AggregateScope
interface UnqualifiedAgg {
  fun target(): UnqualifiedA

  @Component.Builder
  interface Builder {
    fun build(): UnqualifiedAgg
  }
}
