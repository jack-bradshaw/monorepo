package com.jackbradshaw.backstab.core.parser

import dagger.Module
import dagger.Provides

@Module
object ParserModule {
  @Provides
  fun provideParser(): Parser = ParserImpl()
}
