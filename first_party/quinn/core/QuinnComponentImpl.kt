package com.jackbradshaw.quinn.core

import com.jackbradshaw.quinn.QuinnScope
import dagger.Binds
import dagger.Component
import dagger.Module

/** Default [QuinnComponent]. */
@QuinnScope
@Component(modules = [QuinnComponentImpl.QuinnModule::class])
interface QuinnComponentImpl : QuinnComponent {

  @Module
  interface QuinnModule {
    @Binds fun bindFactory(impl: QuinnImpl.FactoryImpl): Quinn.Factory
  }
}

/** Provides a new [QuinnComponent]. */
fun quinnComponent(): QuinnComponent = DaggerQuinnComponentImpl.create()
