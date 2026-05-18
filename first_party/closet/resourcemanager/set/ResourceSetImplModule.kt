package com.jackbradshaw.closet.resourcemanager.set

import dagger.Binds
import dagger.Module

@Module
interface ResourceSetImplModule {
  @Binds fun bindResourceSetFactory(impl: ResourceSetImpl.FactoryImpl): ResourceSet.Factory
}
