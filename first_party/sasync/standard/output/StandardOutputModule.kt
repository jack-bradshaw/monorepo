package com.jackbradshaw.sasync.standard.output

import com.jackbradshaw.sasync.outbound.transport.OutboundTransport
import com.jackbradshaw.sasync.standard.StandardScope
import dagger.Module
import dagger.Provides
import java.io.OutputStream

@Module
object StandardOutputModule {

  @Provides
  @StandardOutput
  @StandardScope
  fun provide(
      @StandardOutput stream: OutputStream,
      factory: OutboundTransport.Factory
  ): OutboundTransport = factory.create(stream)
}
