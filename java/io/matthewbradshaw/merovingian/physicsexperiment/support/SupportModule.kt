package io.matthewbradshaw.merovingian.physicsexperiment.support

import dagger.Provides
import dagger.Module
import io.matthewbradshaw.merovingian.physicsexperiment.PhysicsExperimentScope
import kotlin.random.Random

@Module
class SupportModule {
  @Provides
  @PhysicsExperimentScope
  fun provideRandom() = Random(0L)
}