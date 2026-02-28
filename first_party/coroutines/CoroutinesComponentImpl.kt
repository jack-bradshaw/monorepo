package com.jackbradshaw.coroutines

import com.jackbradshaw.coroutines.io.IoModule
import dagger.Component

@CoroutinesScope
@Component(modules = [IoModule::class])
interface CoroutinesComponentImpl : CoroutinesComponent

fun coroutinesComponent(): CoroutinesComponent = DaggerCoroutinesComponentImpl.create()
