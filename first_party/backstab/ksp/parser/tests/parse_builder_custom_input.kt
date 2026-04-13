package com.foo

import com.jackbradshaw.backstab.core.annotations.Backstab
import dagger.Component
import javax.inject.Named

@Backstab
@Component
interface ComponentBuilderCustom {
  @Component.Builder
  interface Builder {
    fun setFoo(@Named("foo") foo: String): Builder

    fun execute(): ComponentBuilderCustom
  }
}
