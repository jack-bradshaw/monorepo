package io.matthewbradshaw.merovingian.host

import dagger.Binds
import dagger.Module

@Module
interface HostModule {
  @Binds
  fun bindHost(impl: HostImpl): Host
}