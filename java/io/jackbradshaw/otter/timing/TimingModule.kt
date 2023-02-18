package io.jackbradshaw.otter.timing

import dagger.Binds
import dagger.Module
import io.jackbradshaw.otter.qualifiers.Physics
<<<<<<< HEAD
=======
import io.jackbradshaw.otter.qualifiers.Rendering
>>>>>>> 780513c7d14aae85c67b233f1c2667ee1e78f25b

@Module
interface TimingModule {
  @Binds @Rendering fun bindRendering(impl: RenderingClock): Clock

  @Binds @Physics fun bindPhysics(impl: PhysicsClock): Clock
}
