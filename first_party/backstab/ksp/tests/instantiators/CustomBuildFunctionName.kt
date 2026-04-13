package com.jackbradshaw.backstab.ksp.tests.instantiators

import com.jackbradshaw.backstab.core.annotations.AggregateScope
import com.jackbradshaw.backstab.core.annotations.Backstab
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Inject

class CustomBuildFoo @Inject constructor()

@Module
object CustomBuildModule {
  val instance = CustomBuildFoo()

  @Provides fun provide(): CustomBuildFoo = instance
}

@Component(modules = [CustomBuildModule::class])
@Backstab
interface CustomBuildA {
  fun foo(): CustomBuildFoo

  @Component.Builder
  interface Builder {
    fun execute(): CustomBuildA
  }
}

@Component(modules = [CustomBuildA_BackstabModule::class])
@com.jackbradshaw.backstab.core.annotations.AggregateScope
interface CustomBuildAgg {
  fun target(): CustomBuildA

  @Component.Builder
  interface Builder {
    fun execute(): CustomBuildAgg
  }
}
