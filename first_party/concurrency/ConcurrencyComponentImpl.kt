package com.jackbradshaw.concurrency

import com.jackbradshaw.concurrency.pulsar.PulsarModule
import dagger.Component

@ConcurrencyScope
@Component(modules = [PulsarModule::class])
interface ConcurrencyComponentImpl : ConcurrencyComponent

fun concurrencyComponent(): ConcurrencyComponent = DaggerConcurrencyComponentImpl.create()
