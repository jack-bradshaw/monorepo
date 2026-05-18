package com.jackbradshaw.closet.rule

import dagger.Binds
import dagger.Module

@Module
interface AutoCloseRuleModule {
  @Binds fun bindAutoCloseRule(impl: AutoCloseRuleImpl): AutoCloseRule
}
