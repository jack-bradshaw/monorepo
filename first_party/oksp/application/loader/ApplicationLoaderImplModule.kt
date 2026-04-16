package com.jackbradshaw.oksp.application.loader

import dagger.Binds
import dagger.Module

@Module
abstract class ApplicationLoaderImplModule {
  @Binds abstract fun bindApplicationLoader(impl: ApplicationLoaderImpl): ApplicationLoader
}
