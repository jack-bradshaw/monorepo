package com.foo

import com.jackbradshaw.backstab.core.annotations.Backstab
import dagger.Component
import javax.inject.Named

@Backstab
@Component
interface ComponentFactoryCustom {
  @Component.Factory
  interface Factory {
    fun createIt(@Named("foo") foo: String): ComponentFactoryCustom
  }
}
