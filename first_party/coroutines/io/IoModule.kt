package com.jackbradshaw.coroutines.io

import com.jackbradshaw.coroutines.CoroutinesScope
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

@Module
class IoModule {

  @Provides
  @Io
  @CoroutinesScope
  fun provideCoroutineScope(): CoroutineScope = CoroutineScope(Dispatchers.IO)
}
