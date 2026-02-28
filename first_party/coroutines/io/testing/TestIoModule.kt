package com.jackbradshaw.coroutines.io.testing

import com.jackbradshaw.coroutines.CoroutinesScope
import com.jackbradshaw.coroutines.io.Io
import dagger.Binds
import dagger.Module
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.test.TestScope

@Module
interface TestIoModule {
  @Binds @Io @CoroutinesScope fun bindCoroutineScope(impl: TestScope): CoroutineScope
}
