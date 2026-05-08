package com.jackbradshaw.concurrency.pulsar.testing

import com.jackbradshaw.concurrency.pulsar.PulsarScope
import dagger.Component

@PulsarScope
@Component(modules = [TestPulsarModule::class])
interface TestPulsarComponentImpl : TestPulsarComponent

fun testPulsarComponent(): TestPulsarComponent = DaggerTestPulsarComponentImpl.create()
