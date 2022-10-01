package io.jackbradshaw.otter.openxr.manifest.generator

import dagger.Binds
import dagger.Module

@Module
interface ManifestGeneratorModule {
  @Binds fun bindManifestGenerator(impl: ManifestGeneratorImpl): ManifestGenerator
}
