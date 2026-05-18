package com.jackbradshaw.closet.rule

import com.jackbradshaw.closet.ClosetScope
import dagger.Component

@ClosetScope
@Component(modules = [AutoCloseRuleModule::class])
interface AutoCloseRuleComponentImpl : AutoCloseRuleComponent {
  @Component.Builder
  interface Builder {
    fun build(): AutoCloseRuleComponentImpl
  }
}

/** Provides a new [AutoCloseRuleComponent]. */
fun autoCloseRuleComponent(): AutoCloseRuleComponent =
    DaggerAutoCloseRuleComponentImpl.builder().build()
