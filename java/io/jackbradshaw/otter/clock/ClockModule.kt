package io.jackbradshaw.otter.clock

import dagger.Binds

@Module
interface ClockModule {
  @Binds @Rendering fun bindRendering(impl: RenderingClock): Clock

  @Binds @Physics fun bindPhysics(impl: PhysicsClock): Clock

  @Binds @Real fun bindReal(impl: RealClock): Clock
}
