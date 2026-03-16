package com.jackbradshaw.coroutines.standard.testing

import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import com.jackbradshaw.coroutines.io.Io
import com.jackbradshaw.coroutines.io.IoIntermediate
import com.jackbradshaw.coroutines.testing.interceptor.InterceptorDispatcher

/** Provides types related to IO-bound work. */
@Module
object IoModule {
  @Provides
  @Io
  fun provideDispatcher(@IoIntermediate actual: CoroutineDispatcher, interceptorFactory: InterceptorDispatcher.Factory) : CoroutineDispatcher {
    return interceptorFactory.create(actual)
  }

  @Provides
  @Io
  fun provideScope(@Io dispatcher: CoroutineDispatcher) : CoroutineScope {
    return CoroutineScope(dispatcher)
  }
}