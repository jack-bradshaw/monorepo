package com.jackbradshaw.closet.resourcemanager.map

import dagger.Binds
import dagger.Module

@Module
interface ResourceMapImplModule {
  @Binds fun bindResourceMapFactory(impl: ResourceMapImpl.FactoryImpl): ResourceMap.Factory
}
