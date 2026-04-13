package com.jackbradshaw.backstab.ksp.tests.misc

import com.jackbradshaw.backstab.core.annotations.AggregateScope
import com.jackbradshaw.backstab.core.annotations.Backstab
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Inject

class ModuleProvidedRootFoo @Inject constructor()

@javax.inject.Scope annotation class TestScope

@Component
interface ModuleProvidedRootB {
  fun foo(): ModuleProvidedRootFoo

  @Component.Builder
  interface Builder {
    fun build(): ModuleProvidedRootB
  }
}

@Module
object ModuleProvidedRootModule1 {
  val instance = ModuleProvidedRootFoo()

  @Provides fun provideFoo(): ModuleProvidedRootFoo = instance

  @Provides fun provideB(): ModuleProvidedRootB = DaggerModuleProvidedRootB.builder().build()
}

@Component(dependencies = [ModuleProvidedRootB::class])
@Backstab
@TestScope
interface ModuleProvidedRootA {
  fun foo(): ModuleProvidedRootFoo

  @Component.Builder
  interface Builder {
    fun build(): ModuleProvidedRootA

    fun b(b: ModuleProvidedRootB): Builder
  }
}

@Component(modules = [ModuleProvidedRootA_BackstabModule::class, ModuleProvidedRootModule1::class])
@AggregateScope
interface ModuleProvidedRootAgg {
  fun target(): ModuleProvidedRootA

  fun foo(): ModuleProvidedRootFoo

  @Component.Builder
  interface Builder {
    fun build(): ModuleProvidedRootAgg
  }
}
