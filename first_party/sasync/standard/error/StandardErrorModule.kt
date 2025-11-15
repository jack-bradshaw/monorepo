package com.jackbradshaw.sasync.standard.error

import com.jackbradshaw.sasync.outbound.transport.OutboundTransport
import com.jackbradshaw.sasync.standard.StandardScope
import dagger.Module
import dagger.Provides
import java.io.OutputStream

@Module
object StandardErrorModule {

  @Provides
  @StandardError
  @StandardScope
  fun provideOutbound(
      @StandardError stream: OutputStream,
      factory: OutboundTransport.Factory
  ): OutboundTransport = factory.create(stream)
}
