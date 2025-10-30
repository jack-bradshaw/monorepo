package com.jackbradshaw.sasync.standard.input

import com.jackbradshaw.sasync.SasyncScope
import com.jackbradshaw.sasync.transport.inbound.Inbound
import com.jackbradshaw.sasync.transport.inbound.InboundImplFactory
import dagger.Module
import dagger.Provides
import java.io.InputStream

@Module
object StandardInputModule {

  @Provides
  @StandardInput
  @SasyncScope
  fun provideInbound(
      @StandardInput stream: InputStream,
      inboundFactory: InboundImplFactory
  ): Inbound {
    return inboundFactory.create(stream)
  }
}
