package com.jackbradshaw.coroutines.testing

import com.jackbradshaw.coroutines.CoroutinesScope
import com.jackbradshaw.coroutines.io.testing.TestIoModule
import com.jackbradshaw.coroutines.testing.launcher.LauncherModule
import com.jackbradshaw.coroutines.testing.scope.ScopeModule
import dagger.Component

@CoroutinesScope
@Component(modules = [TestIoModule::class, ScopeModule::class, LauncherModule::class])
interface TestCoroutinesComponentImpl : TestCoroutinesComponent

fun testCoroutinesComponent(): TestCoroutinesComponent = DaggerTestCoroutinesComponentImpl.create()
