package com.jackbradshaw.kale.provider

import dagger.Binds
import dagger.Module

@Module
interface ProviderChassisModule {
  @Binds fun bindProviderChassis(impl: ProviderChassisImpl): ProviderChassis
}
