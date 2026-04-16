package com.jackbradshaw.kale.provider

import dagger.Binds
import dagger.Module

@Module
interface ProviderRunnerModule {
  @Binds fun bindProviderRunner(impl: ProviderRunnerImpl): ProviderRunner
}
