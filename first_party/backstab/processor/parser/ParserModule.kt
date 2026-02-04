package com.jackbradshaw.backstab.processor.parser

import dagger.Binds
import dagger.Module

/** Dagger module for the Parser package. */
@Module
interface ParserModule {
  /** Binds the concrete implementation of [Parser]. */
  @Binds fun bindParser(impl: ParserImpl): Parser
}
