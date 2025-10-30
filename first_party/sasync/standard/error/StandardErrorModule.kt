package com.jackbradshaw.sasync.standard.error

import com.jackbradshaw.sasync.SasyncScope
import com.jackbradshaw.sasync.transport.outbound.Outbound
import com.jackbradshaw.sasync.transport.outbound.OutboundImplFactory
import dagger.Module
import dagger.Provides
import java.io.OutputStream

@Module
object StandardErrorModule {

  @Provides
  @StandardError
  @SasyncScope
  fun provideOutbound(
      @StandardError stream: OutputStream,
      outboundFactory: OutboundImplFactory
  ): Outbound {
    return outboundFactory.create(stream)
  }
}
