package com.jackbradshaw.sasync.transport.config

import com.jackbradshaw.sasync.config.SasyncConfigModule
import com.jackbradshaw.sasync.transport.inbound.config.Config as InboundConfig
import com.jackbradshaw.sasync.transport.outbound.config.Config as OutboundConfig
import dagger.Module
import dagger.Provides

@Module(includes = [SasyncConfigModule::class])
object TransportConfigModule {

  @Provides
  fun provideInboundConfig(config: Config): InboundConfig {
    return config.getInboundConfig()
  }

  @Provides
  fun provideOutboundConfig(config: Config): OutboundConfig {
    return config.getOutboundConfig()
  }
}
