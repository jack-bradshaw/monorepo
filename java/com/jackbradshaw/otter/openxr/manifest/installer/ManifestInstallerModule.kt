package com.jackbradshaw.otter.openxr.manifest.installer

import dagger.Binds
import dagger.Module

@Module
interface ManifestInstallerModule {
  @Binds fun bindManifestInstaller(impl: ManifestInstallerImpl): ManifestInstaller
}
