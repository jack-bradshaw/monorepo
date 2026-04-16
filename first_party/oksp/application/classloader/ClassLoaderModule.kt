package com.jackbradshaw.oksp.application.classloader

import dagger.Module
import dagger.Provides
import java.net.URLClassLoader

@Module
object ClassLoaderModule {
  @Provides
  fun provideClassLoader(): ClassLoader {
    val loader =
        Thread.currentThread().contextClassLoader as? URLClassLoader
            ?: this::class.java.classLoader as? URLClassLoader

    return loader
        ?: throw IllegalStateException(
            "Could not extract a URLClassLoader from the current thread or class.")
  }
}
