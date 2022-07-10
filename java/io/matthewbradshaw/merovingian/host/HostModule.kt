package io.matthewbradshaw.merovingian.host

import dagger.Module
import dagger.Binds

@Module
interface HostModule {
  @Binds
  fun bindHost(impl: HostImpl): Host
}