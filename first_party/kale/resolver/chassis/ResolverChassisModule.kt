package com.jackbradshaw.kale.resolver.chassis

import dagger.Binds
import dagger.Module

@Module
interface ResolverChassisModule {
  @Binds fun bindChassis(impl: ResolverChassisImpl): ResolverChassis
}
