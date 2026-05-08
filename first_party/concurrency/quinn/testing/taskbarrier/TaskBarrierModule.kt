package com.jackbradshaw.concurrency.quinn.testing.taskbarrier

import com.jackbradshaw.chronosphere.testingtaskbarrier.TestingTaskBarrier
import com.jackbradshaw.concurrency.quinn.QuinnScope
import com.jackbradshaw.concurrency.quinn.QuinnSpecific
import com.jackbradshaw.concurrency.quinn.testing.hub.IdleableQuinnHub
import dagger.Module
import dagger.Provides

@Module
object TestingTaskBarrierModule {
  @Provides
  @QuinnScope
  @QuinnSpecific
  fun provideCoroutinesTestingTaskBarrier(
      taskBarrierFactory: TestingTaskBarrier.Factory,
      quinnHub: IdleableQuinnHub
  ): TestingTaskBarrier = taskBarrierFactory.create(setOf(quinnHub))
}
