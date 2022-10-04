package io.jackbradshaw.otter.timing

import dagger.Binds
import dagger.Module
import io.jackbradshaw.otter.qualifiers.Rendering
import io.jackbradshaw.otter.qualifiers.Physics
import io.jackbradshaw.otter.qualifiers.Host

@Module
interface TimingModule {
  @Binds @Rendering fun bindRendering(impl: RenderingClock): Clock

  @Binds @Physics fun bindPhysics(impl: PhysicsClock): Clock

  @Binds @Host fun bindReal(impl: HostClock): Clock
}
