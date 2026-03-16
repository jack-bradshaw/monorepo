package com.jackbradshaw.closet.resourcemanager

import dagger.Binds
import dagger.Module

@Module
interface ResourceManagerImplModule {
  @Binds
  fun bindResourceManagerFactory(impl: ResourceManagerFactoryImpl): ResourceManager.Factory
}
