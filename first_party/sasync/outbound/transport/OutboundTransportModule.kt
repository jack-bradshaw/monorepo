package com.jackbradshaw.sasync.outbound.transport

import com.jackbradshaw.sasync.outbound.OutboundScope
import dagger.Module
import dagger.Provides

@Module
object OutboundTransportModule {
  @Provides
  @OutboundScope
  fun provide(factory: OutboundTransportImpl.Factory): OutboundTransport.Factory =
      OutboundTransport.Factory { stream -> factory.create(stream) }
}
