package io.jackbradshaw.otter.coroutines

import dagger.Module
import dagger.Provides
import io.jackbradshaw.otter.engine.Engine
import io.jackbradshaw.otter.qualifiers.Rendering
import io.jackbradshaw.otter.qualifiers.Physics

@Module
object CoroutinesModule {
  @Provides @Rendering fun provideRenderingDispatcher(engine: Engine) = engine.renderingDispatcher()

  @Provides @Physics fun providePhysicsDispatcher(engine: Engine) = engine.physicsDispatcher()
}
