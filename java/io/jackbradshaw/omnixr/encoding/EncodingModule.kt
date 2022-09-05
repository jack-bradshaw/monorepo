package io.jackbradshaw.omnixr.encoding

import dagger.Module
import dagger.Binds

@Module
interface EncodingModule {
  @Binds
  fun bindEncoding(impl: EncodingImpl): Encoding
}