package io.jackbradshaw.otter.openxr.manifest.generator

import dagger.Module
import dagger.Binds

@Module
interface ManifestGeneratorModule {
  @Binds
  fun bindManifestGenerator(impl: ManifestGeneratorImpl): ManifestGenerator
}