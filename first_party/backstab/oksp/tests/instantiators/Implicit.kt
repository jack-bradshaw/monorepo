package com.jackbradshaw.backstab.oksp.tests.instantiators

import com.jackbradshaw.backstab.core.annotations.Backstab
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Inject

class ImplicitFoo @Inject constructor()

@Module
object ImplicitModule {
  val instance = ImplicitFoo()

  @Provides fun provide(): ImplicitFoo = instance
}

@Component(modules = [ImplicitModule::class])
@Backstab
interface ImplicitA {
  fun foo(): ImplicitFoo
}
