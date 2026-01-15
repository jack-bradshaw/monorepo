package com.jackbradshaw.concurrency.testing

import com.jackbradshaw.concurrency.ConcurrencyScope
import com.jackbradshaw.concurrency.pulsar.testing.TestPulsarModule
import dagger.Component

@ConcurrencyScope
@Component(modules = [TestPulsarModule::class])
interface TestConcurrencyComponentImpl : TestConcurrencyComponent

fun testConcurrencyComponent(): TestConcurrencyComponent =
    DaggerTestConcurrencyComponentImpl.create()
