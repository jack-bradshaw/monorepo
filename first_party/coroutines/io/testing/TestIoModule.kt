package com.jackbradshaw.coroutines.io.testing

import com.jackbradshaw.coroutines.CoroutinesScope
import com.jackbradshaw.coroutines.io.Io
import com.jackbradshaw.coroutines.testing.scope.ScopeModule
import dagger.Binds
import dagger.Module
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.test.TestScope

@Module(includes = [ScopeModule::class])
interface TestIoModule {
  @Binds @Io @CoroutinesScope fun bindCoroutineScope(impl: TestScope): CoroutineScope
}
