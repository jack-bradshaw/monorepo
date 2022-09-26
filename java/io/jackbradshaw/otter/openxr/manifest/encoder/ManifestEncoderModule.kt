package io.jackbradshaw.otter.openxr.manifest.encoder

import dagger.Module
import dagger.Binds

@Module
interface ManifestEncoderModule {
  @Binds
  fun bindEncoder(impl: ManifestEncoderImpl): ManifestEncoder
}