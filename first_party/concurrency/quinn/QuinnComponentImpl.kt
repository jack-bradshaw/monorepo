
package com.jackbradshaw.concurrency.quinn


import dagger.Binds
import dagger.Component
import dagger.Module

/** Default [QuinnComponent]. */
@QuinnScope
@Component(modules = [QuinnProductionModule::class, QuinnComponentImpl.DefaultModule::class])
interface QuinnComponentImpl : QuinnComponent {

  @Module
  interface DefaultModule {
    @Binds fun bindDefaultFactory(@Production impl: Quinn.Factory): Quinn.Factory
  }
}


/** Provides a new [QuinnComponent]. */
fun quinnComponent(): QuinnComponent = DaggerQuinnComponentImpl.create()
