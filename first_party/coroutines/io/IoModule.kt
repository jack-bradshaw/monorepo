package com.jackbradshaw.coroutines.io

import com.jackbradshaw.coroutines.Io
import dagger.Module
import dagger.Provides
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
class IoModule {
  @Provides
  @Io
  fun provideContext(@Io dispatcher: CoroutineDispatcher): CoroutineContext = dispatcher

  @Provides @Io fun provideDispatcher(): CoroutineDispatcher = Dispatchers.IO
}
