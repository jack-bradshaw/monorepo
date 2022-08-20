package io.jackbradshaw.otter.physics.experiment.support

import dagger.Provides
import io.jackbradshaw.otter.physics.experiment.PhysicsExperimentScope
import kotlin.random.Random

@Module
class SupportModule {
  @Provides
  @PhysicsExperimentScope
  fun provideRandom() = Random(0L)
}