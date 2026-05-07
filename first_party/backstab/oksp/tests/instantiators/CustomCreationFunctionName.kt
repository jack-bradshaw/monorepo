package com.jackbradshaw.backstab.oksp.tests.instantiators

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

@Component
interface DummyComponent {
  fun foo(): CustomCreationFoo
}

// foo
// bar
