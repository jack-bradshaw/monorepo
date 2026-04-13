package com.jackbradshaw.backstab.ksp.tests.instantiators

import com.jackbradshaw.backstab.core.annotations.AggregateScope
import com.jackbradshaw.backstab.core.annotations.Backstab
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Inject

class CustomCreationFoo @Inject constructor()

@Module
object CustomCreationModule {
  val instance = CustomCreationFoo()

  @Provides fun provide(): CustomCreationFoo = instance
}

@Component(modules = [CustomCreationModule::class])
@Backstab
interface CustomCreationA {
  fun foo(): CustomCreationFoo
}

@Component(modules = [CustomCreationA_BackstabModule::class])
@com.jackbradshaw.backstab.core.annotations.AggregateScope
interface CustomCreationAgg {
  fun target(): CustomCreationA

  @Component.Builder
  interface Builder {
    fun build(): CustomCreationAgg
  }

  companion object {
    fun execute(): CustomCreationAgg = DaggerCustomCreationAgg.builder().build()
  }
}
