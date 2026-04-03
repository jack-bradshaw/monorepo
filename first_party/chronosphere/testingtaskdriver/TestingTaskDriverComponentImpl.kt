package com.jackbradshaw.chronosphere.testingtaskdriver

import dagger.Component

@Component(modules = [TestingTaskDriverModule::class])
interface TestingTaskDriverComponentImpl : TestingTaskDriverComponent

fun testingTaskDriverComponent(): TestingTaskDriverComponent =
    DaggerTestingTaskDriverComponentImpl.create()
