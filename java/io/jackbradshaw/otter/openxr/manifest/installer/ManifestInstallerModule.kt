package io.jackbradshaw.otter.openxr.manifest.installer

import dagger.Binds

@Module
interface ManifestInstallerModule {
  @Binds
  fun bindManifestInstaller(impl: ManifestInstallerImpl): ManifestInstaller
}