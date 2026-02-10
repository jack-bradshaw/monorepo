package com.jackbradshaw.sasync.outbound

import com.jackbradshaw.concurrency.Concurrency
import com.jackbradshaw.concurrency.concurrency
import com.jackbradshaw.coroutines.Coroutines
import com.jackbradshaw.coroutines.coroutines
import com.jackbradshaw.sasync.outbound.config.Config
import com.jackbradshaw.sasync.outbound.config.defaultConfig
import com.jackbradshaw.sasync.outbound.transport.OutboundTransport
import com.jackbradshaw.sasync.outbound.transport.OutboundTransportModule
import dagger.BindsInstance
import dagger.Component

@OutboundScope
@Component(
    modules = [OutboundTransportModule::class],
    dependencies = [Concurrency::class, Coroutines::class])
interface Outbound {

  fun provideOutboundTransportFactory(): OutboundTransport.Factory

  @Component.Builder
  interface Builder {

    @BindsInstance fun binding(config: Config): Builder

    fun consuming(concurrency: Concurrency): Builder

    fun consuming(coroutines: Coroutines): Builder

    fun build(): Outbound
  }
}

fun outbound(
    config: Config = defaultConfig,
    concurrency: Concurrency = concurrency(),
    coroutines: Coroutines = coroutines(),
): Outbound =
    DaggerOutbound.builder().binding(config).consuming(concurrency).consuming(coroutines).build()
