package com.jackbradshaw.concurrency.pulsar.testing

import com.jackbradshaw.concurrency.pulsar.Pulsar
import dagger.Binds
import dagger.Module

@Module
interface TestPulsarModule {
  @Binds fun bindTestPulsar(impl: TestPulsarImpl): TestPulsar

  @Binds fun bindPulsar(impl: TestPulsarImpl): Pulsar
}
