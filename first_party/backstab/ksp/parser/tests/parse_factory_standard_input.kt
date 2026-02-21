package com.foo

import com.jackbradshaw.backstab.core.annotations.Backstab
import dagger.Component
import javax.inject.Named

@Backstab
@Component
interface ComponentFactoryStandard {
  @Component.Factory
  interface Factory {
    fun factory(@Named("foo") foo: String): ComponentFactoryStandard
  }
}
