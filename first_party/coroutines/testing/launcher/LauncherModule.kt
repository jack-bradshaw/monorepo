package com.jackbradshaw.coroutines.testing.launcher

import com.jackbradshaw.coroutines.testing.dispatchers.DispatchersModule
import dagger.Binds
import dagger.Module

@Module(includes = [DispatchersModule::class])
interface LauncherModule {
  @Binds fun bind(impl: LauncherImpl): Launcher
}
