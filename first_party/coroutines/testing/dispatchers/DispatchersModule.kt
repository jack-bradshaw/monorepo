package com.jackbradshaw.coroutines.testing.dispatchers

import com.jackbradshaw.coroutines.CoroutinesScope
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher

@Module
@OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
object DispatchersModule {

  @Provides
  @CoroutinesScope
  fun provideScheduler(): TestCoroutineScheduler = TestCoroutineScheduler()

  @Provides
  @Deferred
  @CoroutinesScope
  fun provideDeferredDispatcher(scheduler: TestCoroutineScheduler): TestDispatcher =
      StandardTestDispatcher(scheduler)

  @Provides
  @Eager
  @CoroutinesScope
  fun provideEagerDispatcher(scheduler: TestCoroutineScheduler): TestDispatcher =
      UnconfinedTestDispatcher(scheduler)
}
