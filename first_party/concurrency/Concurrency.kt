package com.jackbradshaw.concurrency

import com.jackbradshaw.concurrency.pulsar.Pulsar
import com.jackbradshaw.concurrency.pulsar.PulsarModule
import dagger.Component

@Component(modules = [PulsarModule::class])
@ConcurrencyScope
interface Concurrency {
  fun pulsar(): Pulsar
}
