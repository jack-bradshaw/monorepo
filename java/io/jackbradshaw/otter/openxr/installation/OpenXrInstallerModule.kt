package io.jackbradshaw.otter.openxr.installation

import dagger.Binds
import dagger.Module

@Module
interface OpenXrInstallerModule {
  @Binds
  fun bindOpenXrInstaller(impl: OpenXrInstallerImpl): OpenXrInstaller
}