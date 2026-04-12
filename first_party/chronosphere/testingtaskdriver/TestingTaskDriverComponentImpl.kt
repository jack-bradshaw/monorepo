package com.jackbradshaw.chronosphere.testingtaskdriver

import dagger.Component

/** Default implementation of [TestingTaskDriverComponent]. */
@Component(modules = [TestingTaskDriverModule::class])
interface TestingTaskDriverComponentImpl : TestingTaskDriverComponent

/** Creates a new instance of [TestingTaskDriverComponentImpl]. */
fun testingTaskDriverComponent(): TestingTaskDriverComponent =
    DaggerTestingTaskDriverComponentImpl.create()
