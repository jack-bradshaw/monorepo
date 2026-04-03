package com.jackbradshaw.coroutines

import com.jackbradshaw.coroutines.cpu.CpuModule
import com.jackbradshaw.coroutines.io.IoModule
import dagger.Component

/** [CoroutinesComponent] that provides the standard Kotlin IO and CPU dispatchers. */
@CoroutinesDaggerScope
@Component(modules = [CpuModule::class, IoModule::class])
interface CoroutinesComponentImpl : CoroutinesComponent

/** Provides the default implementation of [CoroutinesComponent]. */
fun coroutinesComponent(): CoroutinesComponent = DaggerCoroutinesComponentImpl.create()
