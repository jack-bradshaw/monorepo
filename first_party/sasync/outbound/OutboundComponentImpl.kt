package com.jackbradshaw.sasync.outbound

import com.jackbradshaw.concurrency.pulsar.PulsarComponent
import com.jackbradshaw.concurrency.pulsar.pulsarComponent
import com.jackbradshaw.coroutines.CoroutinesComponent
import com.jackbradshaw.coroutines.coroutinesComponent
import com.jackbradshaw.sasync.outbound.config.Config
import com.jackbradshaw.sasync.outbound.transport.OutboundTransportModule
import dagger.BindsInstance
import dagger.Component

@OutboundScope
@Component(
    dependencies = [CoroutinesComponent::class, PulsarComponent::class],
    modules = [OutboundTransportModule::class])
interface OutboundComponentImpl : OutboundComponent {
  @Component.Builder
  interface Builder {
    fun consuming(coroutines: CoroutinesComponent): Builder

    fun consuming(pulsar: PulsarComponent): Builder

    @BindsInstance fun binding(config: Config): Builder

    fun build(): OutboundComponentImpl
  }
}

fun outboundComponent(
    config: Config,
    coroutines: CoroutinesComponent = coroutinesComponent(),
    pulsar: PulsarComponent = pulsarComponent()
): OutboundComponent =
    DaggerOutboundComponentImpl.builder()
        .binding(config)
        .consuming(coroutines)
        .consuming(pulsar)
        .build()
