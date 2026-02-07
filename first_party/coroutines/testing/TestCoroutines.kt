package com.jackbradshaw.coroutines.testing

import com.jackbradshaw.coroutines.Coroutines
import com.jackbradshaw.coroutines.CoroutinesScope
import com.jackbradshaw.coroutines.io.testing.TestIoModule
import com.jackbradshaw.coroutines.testing.launcher.Launcher
import com.jackbradshaw.coroutines.testing.launcher.LauncherModule
import com.jackbradshaw.coroutines.testing.scope.ScopeModule
import dagger.Component
import kotlinx.coroutines.test.TestScope

@CoroutinesScope
@Component(modules = [TestIoModule::class, ScopeModule::class, LauncherModule::class])
interface TestCoroutines : Coroutines {
  fun testScope(): TestScope

  fun launcher(): Launcher
}
