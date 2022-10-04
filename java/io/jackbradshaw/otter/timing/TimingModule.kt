package io.jackbradshaw.otter.timing

import dagger.Binds
import dagger.Module
import io.jackbradshaw.otter.qualifiers.Rendering
import io.jackbradshaw.otter.qualifiers.Physics
import io.jackbradshaw.otter.qualifiers.Real

@Module
interface TimingModule {
  @Binds @Rendering fun bindRendering(impl: RenderingClock): Clock

  @Binds @Physics fun bindPhysics(impl: PhysicsClock): Clock

  @Binds @Real fun bindReal(impl: RealClock): Clock
}
