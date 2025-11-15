package com.jackbradshaw.sasync.standard.input

import com.jackbradshaw.sasync.inbound.transport.InboundTransport
import com.jackbradshaw.sasync.standard.StandardScope
import dagger.Module
import dagger.Provides
import java.io.InputStream

@Module
object StandardInputModule {

  @Provides
  @StandardInput
  @StandardScope
  fun provideInbound(
      @StandardInput stream: InputStream,
      factory: InboundTransport.Factory
  ): InboundTransport = factory.create(stream)
}
