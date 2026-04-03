package com.jackbradshaw.coroutines.testing.artificial.io

import com.jackbradshaw.coroutines.Io
import com.jackbradshaw.coroutines.testing.artificial.dispatcher.AdvancableDispatcher
import dagger.Binds
import dagger.Module
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.CoroutineDispatcher


@Module
interface TestingIoModule {
  @Binds @Io fun bindIoContext(dispatcher: AdvancableDispatcher): CoroutineContext

  @Binds
  @Io
  fun bindIoCoroutineDispatcher(dispatcher: AdvancableDispatcher): CoroutineDispatcher
}
