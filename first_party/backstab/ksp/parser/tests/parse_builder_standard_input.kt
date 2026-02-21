package com.foo

import com.jackbradshaw.backstab.core.annotations.Backstab
import dagger.Component
import javax.inject.Named

@Backstab
@Component
interface ComponentBuilderStandard {
  @Component.Builder
  interface Builder {
    fun setFoo(@Named("foo") foo: String): Builder

    fun build(): ComponentBuilderStandard
  }
}
