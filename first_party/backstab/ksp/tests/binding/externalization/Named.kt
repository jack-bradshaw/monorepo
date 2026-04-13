package com.jackbradshaw.backstab.ksp.tests.binding.externalization

import com.jackbradshaw.backstab.core.annotations.AggregateScope
import com.jackbradshaw.backstab.core.annotations.Backstab
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Inject
import javax.inject.Named

class NamedFoo @Inject constructor()

@Module
object NamedModule {
  val instance = NamedFoo()

  @Provides @Named("id") fun provideFoo(): NamedFoo = instance
}

@Component(modules = [NamedModule::class])
@Backstab
interface NamedA {
  @Named("id") fun foo(): NamedFoo

  @Component.Builder
  interface Builder {
    fun build(): NamedA
  }
}

@Component(modules = [NamedA_BackstabModule::class])
@com.jackbradshaw.backstab.core.annotations.AggregateScope
interface NamedAgg {
  fun target(): NamedA

  @Component.Builder
  interface Builder {
    fun build(): NamedAgg
  }
}
