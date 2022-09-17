package io.jackbradshaw.clearxr.manifest.generator

import dagger.Module
import dagger.Binds

@Module
interface ManifestGeneratorModule {
  @Binds
  fun bindManifestGenerator(impl: ManifestGeneratorImpl): ManifestGenerator
}