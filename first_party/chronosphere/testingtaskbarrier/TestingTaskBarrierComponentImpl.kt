package com.jackbradshaw.chronosphere.testingtaskbarrier

import dagger.Component

/** Default implementation of [TestingTaskBarrierComponent]. */
@Component(modules = [TestingTaskBarrierModule::class])
interface TestingTaskBarrierComponentImpl : TestingTaskBarrierComponent

/** Creates a new instance of [TestingTaskBarrierComponentImpl]. */
fun testingTaskBarrierComponent(): TestingTaskBarrierComponent =
    DaggerTestingTaskBarrierComponentImpl.create()
