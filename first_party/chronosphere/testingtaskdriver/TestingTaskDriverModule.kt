package com.jackbradshaw.chronosphere.testingtaskdriver

import dagger.Binds
import dagger.Module

@Module
interface TestingTaskDriverModule {
  @Binds
  fun bindTestingTaskDriverFactory(impl: TestingTaskDriverImpl.Factory): TestingTaskDriver.Factory
}
