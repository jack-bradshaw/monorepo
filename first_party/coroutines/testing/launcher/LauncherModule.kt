package com.jackbradshaw.coroutines.testing.launcher

import dagger.Binds
import dagger.Module

@Module
interface LauncherModule {
  @Binds fun bind(impl: LauncherImpl): Launcher
}
