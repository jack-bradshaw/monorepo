package com.jackbradshaw.coroutines.standard.actual

import dagger.Module
import dagger.Provides
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import com.jackbradshaw.coroutines.io.Io
import com.jackbradshaw.coroutines.io.IoIntermediate

/** Provides types related to IO-bound work. */
@Module
object IoModule {
  @Provides
  @IoIntermediate
  fun provideDispatcher() : CoroutineDispatcher {
    return Dispatchers.IO
  }

  @Provides
  @Io
  fun provideScope(@IoIntermediate dispatcher: CoroutineDispatcher) : CoroutineScope {
    return CoroutineScope(dispatcher)
  }
}