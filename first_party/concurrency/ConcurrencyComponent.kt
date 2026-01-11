package com.jackbradshaw.concurrency

import com.jackbradshaw.concurrency.pulsar.Pulsar
import com.jackbradshaw.concurrency.pulsar.PulsarModule
import dagger.Component

interface ConcurrencyComponent {
  fun pulsar(): Pulsar
}

/** Provides production versions of concurrency utilities. */
@Component(modules = [PulsarModule::class])
@ConcurrencyScope
interface ProdConcurrencyComponent : ConcurrencyComponent {
  @Component.Builder
  interface Builder {
    fun build(): ProdConcurrencyComponent
  }
}

/** Creates an instance of [ConcurrencyComponent]. */
fun concurrencyComponent(): ConcurrencyComponent = DaggerProdConcurrencyComponent.builder().build()
