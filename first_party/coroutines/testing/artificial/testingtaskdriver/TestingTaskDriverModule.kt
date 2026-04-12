package com.jackbradshaw.coroutines.testing.artificial.testingtaskdriver

import com.jackbradshaw.chronosphere.testingtaskdriver.TestingTaskDriver
import com.jackbradshaw.coroutines.testing.Coroutines
import com.jackbradshaw.coroutines.testing.artificial.dispatcher.AdvancableDispatcher
import dagger.Module
import dagger.Provides

@Module
class TestingTaskDriverModule {
  @Provides
  @Coroutines
  fun provideTestingTaskDriver(
      dispatcher: AdvancableDispatcher,
      factory: TestingTaskDriver.Factory
  ): TestingTaskDriver = factory.create(setOf(dispatcher))
}
