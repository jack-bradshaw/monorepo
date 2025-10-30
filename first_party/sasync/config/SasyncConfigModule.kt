package com.jackbradshaw.sasync.config

import com.jackbradshaw.sasync.transport.config.Config as TransportConfig
import dagger.Module
import dagger.Provides

@Module
class SasyncConfigModule {

  @Provides
  fun provideTransportConfig(config: Config): TransportConfig {
    return config.transportConfig
  }
}
