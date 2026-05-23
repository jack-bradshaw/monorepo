package com.jackbradshaw.concurrency.quinn.testing.taskbarrier

import com.jackbradshaw.chronosphere.testingtaskbarrier.TestingTaskBarrier
import com.jackbradshaw.concurrency.quinn.QuinnQualifier
import com.jackbradshaw.concurrency.quinn.testing.idleable.IdleableQuinn
import dagger.Module
import dagger.Provides

@Module
object TestingTaskBarrierModule {
  @Provides
  @QuinnQualifier
  fun provideCoroutinesTestingTaskBarrier(
      taskBarrierFactory: TestingTaskBarrier.Factory,
      quinnHub: IdleableQuinn.Hub
  ): TestingTaskBarrier = taskBarrierFactory.create(setOf(quinnHub))
}
