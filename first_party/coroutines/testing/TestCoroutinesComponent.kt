package com.jackbradshaw.coroutines.testing

import com.jackbradshaw.coroutines.CoroutinesComponent
import com.jackbradshaw.coroutines.testing.launcher.Launcher
import kotlinx.coroutines.test.TestScope

interface TestCoroutinesComponent : CoroutinesComponent {
  fun testScope(): TestScope

  fun launcher(): Launcher
}
