package com.jackbradshaw.backstab.oksp.tests.misc

import com.jackbradshaw.backstab.core.annotations.AggregateScope
import com.jackbradshaw.backstab.core.annotations.Backstab
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Inject
import javax.inject.Scope

class ModuleProvidedLeafFoo @Inject constructor()

@Scope annotation class TestScope

@Component
interface ModuleProvidedLeafB {
  fun foo(): ModuleProvidedLeafFoo

  @Component.Builder
  interface Builder {
    fun build(): ModuleProvidedLeafB
  }
}

@Module
object ModuleProvidedLeafModule1 {
  val instance = ModuleProvidedLeafFoo()

  @Provides fun provideFoo(): ModuleProvidedLeafFoo = instance

  @Provides fun provideB(): ModuleProvidedLeafB = DaggerModuleProvidedLeafB.builder().build()
}

@Component(dependencies = [ModuleProvidedLeafB::class])
@Backstab
@TestScope
interface ModuleProvidedLeafA {
  fun foo(): ModuleProvidedLeafFoo

  @Component.Builder
  interface Builder {
    fun build(): ModuleProvidedLeafA

    fun b(b: ModuleProvidedLeafB): Builder
  }
}

@Component(modules = [ModuleProvidedLeafA_BackstabModule::class, ModuleProvidedLeafModule1::class])
@AggregateScope
interface ModuleProvidedLeafAgg {
  fun target(): ModuleProvidedLeafA

  fun foo(): ModuleProvidedLeafFoo

  @Component.Builder
  interface Builder {
    fun build(): ModuleProvidedLeafAgg
  }
}
