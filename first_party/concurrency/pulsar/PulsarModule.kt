package com.jackbradshaw.concurrency.pulsar

import dagger.Binds
import dagger.Module

@Module
interface PulsarModule {
  @Binds fun bind(impl: PulsarImpl): Pulsar
}
