package com.jackbradshaw.oksp.application.testing

import com.jackbradshaw.oksp.application.Application
import com.jackbradshaw.oksp.application.ApplicationComponent
import dagger.BindsInstance
import dagger.Component

@Component
interface BoundInstanceApplicationComponent : ApplicationComponent {
  @Component.Builder
  interface Builder {
    @BindsInstance fun binding(application: Application): Builder

    fun build(): BoundInstanceApplicationComponent
  }
}
