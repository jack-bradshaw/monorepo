package com.jackbradshaw.coroutines.testing.realistic.io

import com.jackbradshaw.coroutines.CoroutinesDaggerScope
import com.jackbradshaw.coroutines.Io
import com.jackbradshaw.coroutines.testing.realistic.dispatcher.IdleableDispatcher
import com.jackbradshaw.coroutines.testing.realistic.dispatcher.IdleableDispatcherImpl
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
    @Provides
    @CoroutinesDaggerScope
    @Io
    fun provideIoDispatcher(dispatcher: IdleableDispatcherImpl): IdleableDispatcher = dispatcher
  }
}
