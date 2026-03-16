package com.jackbradshaw.coroutines.standard.testing

import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import com.jackbradshaw.coroutines.io.Cpu
import com.jackbradshaw.coroutines.io.CpuIntermediate
import com.jackbradshaw.coroutines.testing.interceptor.InterceptorDispatcher

/** Provides types related to CPU-bound work. */
@Module
object CpuModule {
  @Provides
  @Cpu
  fun provideDispatcher(@CpuIntermediate actual: CoroutineDispatcher, interceptorFactory: InterceptorDispatcher.Factory) : CoroutineDispatcher {
    return interceptorFactory.create(actual)
  }

  @Provides
  @Cpu
  fun provideScope(@Cpu dispatcher: CoroutineDispatcher) : CoroutineScope {
    return CoroutineScope(dispatcher)
  }
}