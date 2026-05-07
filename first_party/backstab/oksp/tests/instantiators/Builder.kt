package com.jackbradshaw.backstab.oksp.tests.instantiators

import com.jackbradshaw.backstab.core.annotations.Backstab
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Inject

class BuilderFoo @Inject constructor()

@Module
object BuilderModule {
  val instance = BuilderFoo()

  @Provides fun provide(): BuilderFoo = instance
}

@Component(modules = [BuilderModule::class])
@Backstab
interface BuilderA {
  fun foo(): BuilderFoo

  @Component.Builder
  interface Builder {
    fun build(): BuilderA
  }
}
