package com.jackbradshaw.coroutines.testing

import com.jackbradshaw.coroutines.Coroutines
import com.jackbradshaw.coroutines.CoroutinesScope
import com.jackbradshaw.coroutines.io.testing.TestIoModule
import com.jackbradshaw.coroutines.testing.advancer.Advancer
import com.jackbradshaw.coroutines.testing.advancer.AdvancerModule
import com.jackbradshaw.coroutines.testing.launcher.Launcher
import com.jackbradshaw.coroutines.testing.launcher.LauncherModule
import com.jackbradshaw.coroutines.testing.scope.ScopeModule
import dagger.Component
import kotlinx.coroutines.test.TestScope

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
    modules = [TestIoModule::class, ScopeModule::class, LauncherModule::class, AdvancerModule::class])
interface TestCoroutines : Coroutines {
  fun testScope(): TestScope

  fun launcher(): Launcher

  fun advancer(): Advancer
}
