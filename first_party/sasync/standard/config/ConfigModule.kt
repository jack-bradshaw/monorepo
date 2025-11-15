package com.jackbradshaw.sasync.standard.config

import com.jackbradshaw.sasync.standard.StandardScope
import dagger.Module
import dagger.Provides

@Module
class ConfigModule {

  @Provides @StandardScope fun provideInboundConfig(config: Config) = config.inboundConfig

  @Provides @StandardScope fun provideOutboundConfig(config: Config) = config.outboundConfig
}
