package io.jackbradshaw.omnixr.manifest.encoder

import dagger.Module
import dagger.Binds

@Module
interface ManifestEncoderModule {
  @Binds
  fun bindEncoder(impl: ManifestEncoderImpl): ManifestEncoder
}