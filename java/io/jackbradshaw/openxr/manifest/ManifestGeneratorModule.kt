package io.jackbradshaw.openxr.manifest

import dagger.Module
import dagger.Binds

@Module
interface ManifestGeneratorModule {
  @Binds
  fun bindManifestGenerator(impl: ManifestGeneratorImpl): ManifestGenerator
}