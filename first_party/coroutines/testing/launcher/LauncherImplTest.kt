package com.jackbradshaw.coroutines.testing.launcher

import com.jackbradshaw.coroutines.CoroutinesScope
import com.jackbradshaw.coroutines.testing.dispatchers.DispatchersModule
import com.jackbradshaw.coroutines.testing.scope.ScopeModule
import dagger.Component
import javax.inject.Inject
import kotlinx.coroutines.test.TestScope

class LauncherImplTest : LauncherTest() {

  @Inject lateinit var subject: Launcher

  @Inject lateinit var testScope: TestScope

  override fun setupSubject() {
    DaggerTestComponent.create().inject(this)
  }

  override fun subject() = subject

  override fun runScheduledWork() {
    testScope.testScheduler.advanceUntilIdle()
  }
}

@CoroutinesScope
@Component(modules = [LauncherModule::class, ScopeModule::class, DispatchersModule::class])
interface TestComponent {
  fun inject(target: LauncherImplTest)
}
