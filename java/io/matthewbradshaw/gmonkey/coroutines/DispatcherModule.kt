package io.matthewbradshaw.gmonkey.coroutines

import com.jme3.app.Application
import dagger.Provides
import dagger.Module

@Module
class DispatcherModule {
  @Provides
  fun provideDispatcher(app: Application) = app.dispatcher()
}