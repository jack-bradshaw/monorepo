package com.jackbradshaw.backstab.oksp.tests.misc

import com.jackbradshaw.backstab.core.annotations.AggregateScope
import com.jackbradshaw.backstab.core.annotations.Backstab
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Inject
import javax.inject.Scope

class NestedAggregateFoo @Inject constructor()

@Scope annotation class TestScope

@Module
object NestedBackstabModule {
  @Provides fun provideFoo(): NestedAggregateFoo = NestedAggregateFoo()
}

@Component(modules = [NestedBackstabModule::class])
@Backstab
@TestScope
interface NestedAggregateA {
  fun foo(): NestedAggregateFoo

  @Component.Builder
  interface Builder {
    fun build(): NestedAggregateA
  }
}

class NestedAggregateOuter {
  @Component(modules = [NestedAggregateA_BackstabModule::class])
  @AggregateScope
  interface Agg {
    fun target(): NestedAggregateA

    @Component.Builder
    interface Builder {
      fun build(): Agg
    }
  }
}
