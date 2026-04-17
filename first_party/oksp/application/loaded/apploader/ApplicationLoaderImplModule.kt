package com.jackbradshaw.oksp.application.loaded.apploader

import dagger.Binds
import dagger.Module

@Module
abstract class ApplicationLoaderImplModule {
  @Binds abstract fun bindApplicationLoader(impl: ApplicationLoaderImpl): ApplicationLoader
}
