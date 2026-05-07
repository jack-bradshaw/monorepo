package com.jackbradshaw.obelisk.oksp.service

import dagger.Binds
import dagger.Module

@Module
interface ServiceModule {
  @Binds fun bindFactory(impl: ServiceImpl.Factory): Service.Factory
}
