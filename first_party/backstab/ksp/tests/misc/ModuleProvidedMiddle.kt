package com.jackbradshaw.backstab.ksp.tests.misc

import com.jackbradshaw.backstab.core.annotations.AggregateScope
import com.jackbradshaw.backstab.core.annotations.Backstab
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Inject

class ModuleProvidedMiddleFoo @Inject constructor()

@javax.inject.Scope annotation class TestScope

@Component
@Backstab
interface ModuleProvidedMiddleC {
  fun foo(): ModuleProvidedMiddleFoo

  @Component.Builder
  interface Builder {
    fun build(): ModuleProvidedMiddleC
  }
}

@Component(dependencies = [ModuleProvidedMiddleC::class])
interface ModuleProvidedMiddleB {
  fun foo(): ModuleProvidedMiddleFoo

  @Component.Builder
  interface Builder {
    fun build(): ModuleProvidedMiddleB

    fun c(c: ModuleProvidedMiddleC): Builder
  }
}

@Module
object ModuleProvidedMiddleModule1 {
  val instance = ModuleProvidedMiddleFoo()

  @Provides fun provideFoo(): ModuleProvidedMiddleFoo = instance

  @Provides
  fun provideB(c: ModuleProvidedMiddleC): ModuleProvidedMiddleB =
      DaggerModuleProvidedMiddleB.builder().c(c).build()
}

@Component(dependencies = [ModuleProvidedMiddleB::class])
@Backstab
@TestScope
interface ModuleProvidedMiddleA {
  fun foo(): ModuleProvidedMiddleFoo

  @Component.Builder
  interface Builder {
    fun build(): ModuleProvidedMiddleA

    fun b(b: ModuleProvidedMiddleB): Builder
  }
}

@Component(
    modules =
        [
            ModuleProvidedMiddleA_BackstabModule::class,
            ModuleProvidedMiddleC_BackstabModule::class,
            ModuleProvidedMiddleModule1::class])
@AggregateScope
interface ModuleProvidedMiddleAgg {
  fun target(): ModuleProvidedMiddleA

  fun foo(): ModuleProvidedMiddleFoo

  @Component.Builder
  interface Builder {
    fun build(): ModuleProvidedMiddleAgg
  }
}
