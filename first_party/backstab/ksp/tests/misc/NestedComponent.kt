package com.jackbradshaw.backstab.ksp.tests.misc

import com.jackbradshaw.backstab.core.annotations.AggregateScope
import com.jackbradshaw.backstab.core.annotations.Backstab
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Inject

class NestedComponentFoo @Inject constructor()

@javax.inject.Scope annotation class TestScope

@Module
object NestedComponentModule {
  @Provides fun provideFoo(): NestedComponentFoo = NestedComponentFoo()
}

class NestedComponentOuter {
  @Component(modules = [NestedComponentModule::class])
  @Backstab
  @TestScope
  interface A {
    fun foo(): NestedComponentFoo

    @Component.Builder
    interface Builder {
      fun build(): A
    }
  }
}

@Component(modules = [NestedComponentOuter_A_BackstabModule::class])
@AggregateScope
interface NestedComponentAgg {
  fun target(): NestedComponentOuter.A

  @Component.Builder
  interface Builder {
    fun build(): NestedComponentAgg
  }
}
