package com.jackbradshaw.coroutines.testing

import com.jackbradshaw.coroutines.CoroutinesScope
import com.jackbradshaw.coroutines.io.testing.TestIoModule
import com.jackbradshaw.coroutines.testing.dispatchers.DispatchersModule
import com.jackbradshaw.coroutines.testing.advancer.AdvancerModule
import com.jackbradshaw.coroutines.testing.launcher.Launcher
import com.jackbradshaw.coroutines.testing.launcher.LauncherModule
import com.jackbradshaw.coroutines.testing.scope.ScopeModule
import dagger.Component

/**
 * [Coroutines] component for use in tests.
 * 
 * The [testScope], [launcher], and [advancer] are integrated such that [Launcher] launches work in
 * [testScope] and [Advancer] advances the virtual clock of [testScope].
 * 
 * 
 */
@CoroutinesScope
@Component(
    modules =
        [
            TestIoModule::class,
            ScopeModule::class,
            LauncherModule::class,
            DispatchersModule::class,
            AdvancerModule::class,
        ])
interface TestCoroutinesComponentImpl : TestCoroutinesComponent

fun testCoroutinesComponent(): TestCoroutinesComponent = DaggerTestCoroutinesComponentImpl.create()
