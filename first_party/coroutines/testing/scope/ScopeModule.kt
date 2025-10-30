package com.jackbradshaw.coroutines.testing.scope

import com.jackbradshaw.coroutines.CoroutinesScope
import com.jackbradshaw.coroutines.testing.dispatchers.Deferred
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.TestScope

@Module
class ScopeModule {
  @Provides
  @CoroutinesScope
  fun provideCoroutineScope(@Deferred dispatcher: TestDispatcher): TestScope = TestScope(dispatcher)
}
