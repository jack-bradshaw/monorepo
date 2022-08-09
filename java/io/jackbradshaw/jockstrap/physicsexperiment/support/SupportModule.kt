package io.jackbradshaw.jockstrap.physics.experiment.support

import dagger.Provides
import dagger.Module
import io.jackbradshaw.jockstrap.physics.experiment.PhysicsExperimentScope
import kotlin.random.Random

@Module
class SupportModule {
  @Provides
  @PhysicsExperimentScope
  fun provideRandom() = Random(0L)
}