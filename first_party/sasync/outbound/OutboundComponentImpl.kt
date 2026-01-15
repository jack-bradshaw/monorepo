package com.jackbradshaw.sasync.outbound

import com.jackbradshaw.concurrency.ConcurrencyComponent
import com.jackbradshaw.concurrency.concurrencyComponent
import com.jackbradshaw.coroutines.CoroutinesComponent
import com.jackbradshaw.coroutines.coroutinesComponent
import com.jackbradshaw.sasync.outbound.config.Config
import com.jackbradshaw.sasync.outbound.transport.OutboundTransportModule
import dagger.BindsInstance
import dagger.Component

@OutboundScope
@Component(
    dependencies = [CoroutinesComponent::class, ConcurrencyComponent::class],
    modules = [OutboundTransportModule::class])
interface OutboundComponentImpl : OutboundComponent {
  @Component.Builder
  interface Builder {
    fun consuming(coroutines: CoroutinesComponent): Builder

    fun consuming(concurrency: ConcurrencyComponent): Builder

    @BindsInstance fun binding(config: Config): Builder

    fun build(): OutboundComponentImpl
  }
}

fun outboundComponent(
    config: Config,
    coroutines: CoroutinesComponent = coroutinesComponent(),
    concurrency: ConcurrencyComponent = concurrencyComponent()
): OutboundComponent =
    DaggerOutboundComponentImpl.builder()
        .binding(config)
        .consuming(coroutines)
        .consuming(concurrency)
        .build()
