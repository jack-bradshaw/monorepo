package com.jackbradshaw.sasync.standard.output

import com.jackbradshaw.sasync.SasyncScope
import com.jackbradshaw.sasync.transport.outbound.Outbound
import com.jackbradshaw.sasync.transport.outbound.OutboundImplFactory
import dagger.Module
import dagger.Provides
import java.io.OutputStream

@Module
object StandardOutputModule {

  @Provides
  @StandardOutput
  @SasyncScope
  fun provide(
      @StandardOutput stream: OutputStream,
      outboundFactory: OutboundImplFactory
  ): Outbound {
    return outboundFactory.create(stream)
  }
}
