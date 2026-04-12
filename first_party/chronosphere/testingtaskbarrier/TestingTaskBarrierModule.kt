package com.jackbradshaw.chronosphere.testingtaskbarrier

import dagger.Binds
import dagger.Module

@Module
interface TestingTaskBarrierModule {
  @Binds
  fun bindTestingTaskBarrierFactory(
      impl: TestingTaskBarrierImpl.Factory
  ): TestingTaskBarrier.Factory
}
