package com.jackbradshaw.backstab.ksp.tests.binding.externalization

import com.jackbradshaw.backstab.core.annotations.AggregateScope
import com.jackbradshaw.backstab.core.annotations.Backstab
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Inject

class UnqualifiedFoo @Inject constructor()

@Module
object UnqualifiedModule {
  val instance = UnqualifiedFoo()

  @Provides fun provideFoo(): UnqualifiedFoo = instance
}

@Component(modules = [UnqualifiedModule::class])
@Backstab
interface UnqualifiedA {
  fun foo(): UnqualifiedFoo

  @Component.Builder
  interface Builder {
    fun build(): UnqualifiedA
  }
}

@Component(modules = [UnqualifiedA_BackstabModule::class])
@com.jackbradshaw.backstab.core.annotations.AggregateScope
interface UnqualifiedAgg {
  fun target(): UnqualifiedA

  @Component.Builder
  interface Builder {
    fun build(): UnqualifiedAgg
  }
}
