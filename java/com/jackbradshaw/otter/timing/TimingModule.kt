package com.jackbradshaw.otter.timing

import dagger.Binds
import dagger.Module
import com.jackbradshaw.otter.qualifiers.Physics
import com.jackbradshaw.otter.qualifiers.Rendering

@Module
interface TimingModule {
  @Binds @Rendering fun bindRendering(impl: RenderingClock): Clock

  @Binds @Physics fun bindPhysics(impl: PhysicsClock): Clock
}
