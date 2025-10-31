package com.jackbradshaw.concurrency

import com.jackbradshaw.concurrency.pulsar.Pulsar
import com.jackbradshaw.concurrency.pulsar.PulsarModule
import dagger.Component

/** Provides production versions of concurrency utilities. */
@Component(modules = [PulsarModule::class])
@ConcurrencyScope
interface Concurrency {
  fun pulsar(): Pulsar
}

/** Creates an instance of [Concurrency]. */
fun concurrency() = DaggerConcurrency.create()
