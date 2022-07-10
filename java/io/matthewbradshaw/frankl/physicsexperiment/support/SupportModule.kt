package io.matthewbradshaw.frankl.physicsexperiment.support

import dagger.Provides
import dagger.Module
import io.matthewbradshaw.frankl.physicsexperiment.PhysicsExperimentScope
import kotlin.random.Random

@Module
class SupportModule {
  @Provides
  @PhysicsExperimentScope
  fun provideRandom() = Random(0L)
}