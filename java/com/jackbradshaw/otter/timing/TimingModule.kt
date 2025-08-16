package com.jackbradshaw.otter.timing

import com.jackbradshaw.otter.qualifiers.Physics
import com.jackbradshaw.otter.qualifiers.Rendering
import dagger.Binds
import dagger.Module

@Module
interface TimingModule {
  @Binds @Rendering fun bindRendering(impl: RenderingClock): Clock

  @Binds @Physics fun bindPhysics(impl: PhysicsClock): Clock
}
