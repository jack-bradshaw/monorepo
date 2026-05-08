package com.jackbradshaw.concurrency.pulsar

import dagger.Component

@PulsarScope
@Component(modules = [PulsarModule::class])
interface PulsarComponentImpl : PulsarComponent

fun pulsarComponent(): PulsarComponent = DaggerPulsarComponentImpl.create()
