package com.jackbradshaw.concurrency.quinn

import dagger.Component

/** Default [QuinnComponent]. */
@QuinnScope
@Component(modules = [QuinnModule::class])
interface QuinnComponentImpl : QuinnComponent

/** Provides a new [QuinnComponent]. */
fun quinnComponent(): QuinnComponent = DaggerQuinnComponentImpl.create()
