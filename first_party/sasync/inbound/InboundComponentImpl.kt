package com.jackbradshaw.sasync.inbound

import com.jackbradshaw.concurrency.pulsar.PulsarComponent
import com.jackbradshaw.concurrency.pulsar.pulsarComponent
import com.jackbradshaw.coroutines.CoroutinesComponent
import com.jackbradshaw.coroutines.coroutinesComponent
import com.jackbradshaw.sasync.inbound.config.Config
import com.jackbradshaw.sasync.inbound.config.defaultConfig
import com.jackbradshaw.sasync.inbound.transport.InboundTransportModule
import dagger.BindsInstance
import dagger.Component

@InboundScope
@Component(
    dependencies = [CoroutinesComponent::class, PulsarComponent::class],
    modules = [InboundTransportModule::class])
interface InboundComponentImpl : InboundComponent {
  @Component.Builder
  interface Builder {
    fun consuming(coroutines: CoroutinesComponent): Builder

    fun consuming(pulsar: PulsarComponent): Builder

    @BindsInstance fun binding(config: Config): Builder

    fun build(): InboundComponentImpl
  }
}

fun inboundComponent(
    config: Config = defaultConfig,
    coroutines: CoroutinesComponent = coroutinesComponent(),
    pulsar: PulsarComponent = pulsarComponent()
): InboundComponent =
    DaggerInboundComponentImpl.builder()
        .binding(config)
        .consuming(coroutines)
        .consuming(pulsar)
        .build()
