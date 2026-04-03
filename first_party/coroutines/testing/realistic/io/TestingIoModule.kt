package com.jackbradshaw.coroutines.testing.realistic.io

import com.jackbradshaw.coroutines.Io
import com.jackbradshaw.coroutines.testing.realistic.dispatcher.IdleableDispatcher
import dagger.Binds
import dagger.Module
import dagger.Provides
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.CoroutineDispatcher


@Module
interface TestingIoModule {

  @Binds @Io fun bindIoContext(@Io dispatcher: IdleableDispatcher): CoroutineContext

  @Binds @Io fun bindIoCoroutineDispatcher(@Io dispatcher: IdleableDispatcher): CoroutineDispatcher

  companion object {
    // Uses 4 threads for testing to mirror common device CPUs.
    @dagger.Provides
    @com.jackbradshaw.coroutines.CoroutinesDaggerScope
    @com.jackbradshaw.coroutines.Io
    fun provideIoDispatcher(factory: IdleableDispatcher.Factory): IdleableDispatcher =
        factory.create(4)
  }
}
