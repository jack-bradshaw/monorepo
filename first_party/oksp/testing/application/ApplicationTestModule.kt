package com.jackbradshaw.oksp.testing.application

import dagger.Binds
import dagger.Module

@Module
abstract class ApplicationTestModule {
  @Binds abstract fun bindApplicationTestRule(impl: ApplicationTestRuleImpl): ApplicationTestRule
}
