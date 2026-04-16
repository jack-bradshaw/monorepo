package com.jackbradshaw.backstab.ksp.tests.binding.internalization

import com.jackbradshaw.backstab.core.annotations.AggregateScope
import com.jackbradshaw.backstab.core.annotations.Backstab
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Inject
import javax.inject.Named

interface NamedFoo

class NamedFooImpl @Inject constructor() : NamedFoo

@Module
object NamedModule {
  val instance = NamedFooImpl()

  @Provides @Named("id") fun provideFoo(): NamedFoo = instance
}

@Component
@Backstab
interface NamedA {
  @Named("id") fun foo(): NamedFoo

  @Component.Builder
  interface Builder {
    @BindsInstance fun foo(@Named("id") foo: NamedFoo): Builder

    fun build(): NamedA
  }
}

@Component(modules = [NamedModule::class, NamedA_BackstabModule::class])
@com.jackbradshaw.backstab.core.annotations.AggregateScope
interface NamedAgg {
  fun target(): NamedA

  @Component.Builder
  interface Builder {
    fun build(): NamedAgg
  }
}
