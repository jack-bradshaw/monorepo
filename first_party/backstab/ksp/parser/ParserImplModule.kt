package com.jackbradshaw.backstab.ksp.parser

import dagger.Binds
import dagger.Module

/** Dagger module for the Parser package. */
@Module
interface ParserImplModule {
  /** Binds the concrete implementation of [Parser]. */
  @Binds fun bindParser(impl: ParserImpl): Parser
}
