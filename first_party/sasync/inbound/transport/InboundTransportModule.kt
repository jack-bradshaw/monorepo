package com.jackbradshaw.sasync.inbound.transport

import com.jackbradshaw.sasync.inbound.InboundScope
import dagger.Module
import dagger.Provides

@Module
object InboundTransportModule {
  @Provides
  @InboundScope
  fun provide(factory: InboundTransportImpl.Factory): InboundTransport.Factory =
      InboundTransport.Factory { stream -> factory.create(stream) }
}
