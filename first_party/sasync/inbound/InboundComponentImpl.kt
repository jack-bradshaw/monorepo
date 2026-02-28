package com.jackbradshaw.sasync.inbound

import com.jackbradshaw.concurrency.ConcurrencyComponent
import com.jackbradshaw.concurrency.concurrencyComponent
import com.jackbradshaw.coroutines.CoroutinesComponent
import com.jackbradshaw.coroutines.coroutinesComponent
import com.jackbradshaw.sasync.inbound.config.Config
import com.jackbradshaw.sasync.inbound.transport.InboundTransportModule
import dagger.BindsInstance
import dagger.Component

@InboundScope
@Component(
    dependencies = [CoroutinesComponent::class, ConcurrencyComponent::class],
    modules = [InboundTransportModule::class])
interface InboundComponentImpl : InboundComponent {
  @Component.Builder
  interface Builder {
    fun consuming(coroutines: CoroutinesComponent): Builder

    fun consuming(concurrency: ConcurrencyComponent): Builder

    @BindsInstance fun binding(config: Config): Builder

    fun build(): InboundComponentImpl
  }
}

fun inboundComponent(
    config: Config,
    coroutines: CoroutinesComponent = coroutinesComponent(),
    concurrency: ConcurrencyComponent = concurrencyComponent()
): InboundComponent =
    DaggerInboundComponentImpl.builder()
        .binding(config)
        .consuming(coroutines)
        .consuming(concurrency)
        .build()
