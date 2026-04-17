package com.jackbradshaw.quinn

import com.jackbradshaw.klu.KluScope
import dagger.Binds
import dagger.Component
import dagger.Module

@KluScope
@Component(modules = [QuinnComponentImpl.QuinnModule::class])
interface QuinnComponentImpl : QuinnComponent {

  @Module
  interface QuinnModule {
    @Binds fun bindFactory(impl: QuinnImpl.FactoryImpl): Quinn.Factory
  }
}