package com.jackbradshaw.coroutines.testing.realistic.testingtaskbarrier

import com.jackbradshaw.chronosphere.testingtaskbarrier.TestingTaskBarrier
import com.jackbradshaw.coroutines.Cpu
import com.jackbradshaw.coroutines.Io
import com.jackbradshaw.coroutines.testing.Coroutines
import com.jackbradshaw.coroutines.testing.realistic.dispatcher.IdleableDispatcher
import dagger.Module
import dagger.Provides

@Module
object TestingTaskBarrierModule {
  @Provides
  @Coroutines
  fun provideCoroutinesTestingTaskBarrier(
      factory: TestingTaskBarrier.Factory,
      @Cpu cpu: IdleableDispatcher,
      @Io io: IdleableDispatcher
  ): TestingTaskBarrier = factory.create(setOf(cpu, io))
}
