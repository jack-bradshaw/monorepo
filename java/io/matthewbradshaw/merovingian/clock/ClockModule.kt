package io.matthewbradshaw.merovingian.clock

import dagger.Binds
import dagger.Module

@Module
interface ClockModule {
  @Binds
  @Rendering
  fun bindRendering(impl: RenderingClock): Clock

  @Binds
  @Physics
  fun bindPhysics(impl: PhysicsClock): Clock
}