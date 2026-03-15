package com.jackbradshaw.closet.resourcemanager

import dagger.Binds
import dagger.Module

@Module
interface ResourceManagerModule {
  @Binds
  fun bindResourceManagerFactory(impl: ResourceManagerFactoryImpl): ResourceManagerFactory
}
