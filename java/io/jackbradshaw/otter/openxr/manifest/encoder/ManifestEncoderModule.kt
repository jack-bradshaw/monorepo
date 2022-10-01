package io.jackbradshaw.otter.openxr.manifest.encoder

import dagger.Binds
import dagger.Module

@Module
interface ManifestEncoderModule {
  @Binds fun bindEncoder(impl: ManifestEncoderImpl): ManifestEncoder
}
