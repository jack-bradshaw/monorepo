package com.jackbradshaw.chronosphere.testingtaskbarrier

import dagger.Component

@Component(modules = [TestingTaskBarrierModule::class])
interface TestingTaskBarrierComponentImpl : TestingTaskBarrierComponent

fun taskBarrierComponent(): TestingTaskBarrierComponent =
    DaggerTestingTaskBarrierComponentImpl.create()
