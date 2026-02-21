package com.jackbradshaw.sasync.inbound

import com.jackbradshaw.concurrency.Concurrency
import com.jackbradshaw.concurrency.concurrency
import com.jackbradshaw.coroutines.Coroutines
import com.jackbradshaw.coroutines.coroutines
import com.jackbradshaw.sasync.inbound.config.Config
import com.jackbradshaw.sasync.inbound.config.defaultConfig
import com.jackbradshaw.sasync.inbound.transport.InboundTransport
import com.jackbradshaw.sasync.inbound.transport.InboundTransportModule
import dagger.BindsInstance
import dagger.Component

@InboundScope
@Component(
    modules = [InboundTransportModule::class],
    dependencies = [Concurrency::class, Coroutines::class])
interface Inbound {

  fun provideInboundTransportFactory(): InboundTransport.Factory

  @Component.Builder
  interface Builder {

    @BindsInstance fun binding(config: Config): Builder

    fun consuming(concurrency: Concurrency): Builder

    fun consuming(coroutines: Coroutines): Builder

    fun build(): Inbound
  }
}

fun inbound(
    config: Config = defaultConfig,
    concurrency: Concurrency = concurrency(),
    coroutines: Coroutines = coroutines(),
): Inbound =
    DaggerInbound.builder().binding(config).consuming(concurrency).consuming(coroutines).build()
