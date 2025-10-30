package com.jackbradshaw.coroutines

import com.jackbradshaw.coroutines.io.Io
import com.jackbradshaw.coroutines.io.IoModule
import dagger.Component
import kotlinx.coroutines.CoroutineScope

/** Kotlin coroutine scopes, components, and other related objects. */
@CoroutinesScope
@Component(modules = [IoModule::class])
interface Coroutines {
  @Io fun ioCoroutineScope(): CoroutineScope
}

/** Creates an instance of [Coroutines]. */
fun coroutines() = DaggerCoroutines.create()
