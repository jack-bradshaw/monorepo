package com.jackbradshaw.sasync.inbound

import com.jackbradshaw.concurrency.ConcurrencyComponent
import com.jackbradshaw.concurrency.concurrencyComponent
import com.jackbradshaw.coroutines.CoroutinesComponent
import com.jackbradshaw.coroutines.coroutinesComponent
import com.jackbradshaw.sasync.inbound.config.Config
import com.jackbradshaw.sasync.inbound.config.defaultConfig
import com.jackbradshaw.sasync.inbound.transport.InboundTransport
import com.jackbradshaw.sasync.inbound.transport.InboundTransportModule
import dagger.BindsInstance
import dagger.Component

interface InboundComponent {
  fun provideInboundTransportFactory(): InboundTransport.Factory
}

@InboundScope
@Component(
    modules = [InboundTransportModule::class],
    dependencies = [ConcurrencyComponent::class, CoroutinesComponent::class])
interface ProdInboundComponent : InboundComponent {
  @Component.Builder
  interface Builder {

    @BindsInstance fun binding(config: Config): Builder

    fun consuming(concurrency: ConcurrencyComponent): Builder

    fun consuming(coroutines: CoroutinesComponent): Builder

    fun build(): ProdInboundComponent
  }
}

fun inboundComponent(
    config: Config = defaultConfig,
    concurrency: ConcurrencyComponent = concurrencyComponent(),
    coroutines: CoroutinesComponent = coroutinesComponent(),
): InboundComponent =
    DaggerProdInboundComponent.builder()
        .binding(config)
        .consuming(concurrency)
        .consuming(coroutines)
        .build()
