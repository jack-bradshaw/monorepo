package com.jackbradshaw.concurrency.quinn.testing.taskbarrier

import com.jackbradshaw.chronosphere.testingtaskbarrier.TestingTaskBarrier
import com.jackbradshaw.concurrency.quinn.Quinn
import com.jackbradshaw.concurrency.quinn.testing.idleable.IdleableQuinn
import dagger.Module
import dagger.Provides
import com.jackbradshaw.concurrency.quinn.QuinnQualifier

@Module
object TestingTaskBarrierModule {
  @Provides
  @QuinnQualifier
  fun provideCoroutinesTestingTaskBarrier(
      taskBarrierFactory: TestingTaskBarrier.Factory,
      quinnHub: IdleableQuinn.Hub
  ): TestingTaskBarrier = taskBarrierFactory.create(setOf(quinnHub))
}
