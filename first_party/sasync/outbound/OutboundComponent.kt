package com.jackbradshaw.sasync.outbound

import com.jackbradshaw.concurrency.ConcurrencyComponent
import com.jackbradshaw.concurrency.concurrencyComponent
import com.jackbradshaw.coroutines.CoroutinesComponent
import com.jackbradshaw.coroutines.coroutinesComponent
import com.jackbradshaw.sasync.outbound.config.Config
import com.jackbradshaw.sasync.outbound.config.defaultConfig
import com.jackbradshaw.sasync.outbound.transport.OutboundTransport
import com.jackbradshaw.sasync.outbound.transport.OutboundTransportModule
import dagger.BindsInstance
import dagger.Component

interface OutboundComponent {
  fun provideOutboundTransportFactory(): OutboundTransport.Factory
}

@OutboundScope
@Component(
    modules = [OutboundTransportModule::class],
    dependencies = [ConcurrencyComponent::class, CoroutinesComponent::class])
interface ProdOutboundComponent : OutboundComponent {

  @Component.Builder
  interface Builder {

    @BindsInstance fun binding(config: Config): Builder

    fun consuming(concurrency: ConcurrencyComponent): Builder

    fun consuming(coroutines: CoroutinesComponent): Builder

    fun build(): ProdOutboundComponent
  }
}

fun outboundComponent(
    config: Config = defaultConfig,
    concurrency: ConcurrencyComponent = concurrencyComponent(),
    coroutines: CoroutinesComponent = coroutinesComponent(),
): OutboundComponent =
    DaggerProdOutboundComponent.builder()
        .binding(config)
        .consuming(concurrency)
        .consuming(coroutines)
        .build()
