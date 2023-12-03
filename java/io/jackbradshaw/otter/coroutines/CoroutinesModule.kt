package io.jackbradshaw.otter.coroutines

import dagger.Module
import dagger.Provides
import io.jackbradshaw.otter.engine.core.EngineCore
import io.jackbradshaw.otter.qualifiers.Physics
import io.jackbradshaw.otter.qualifiers.Rendering

@Module
object CoroutinesModule {
  @Provides
  @Rendering
  fun provideRenderingDispatcher(engineCore: EngineCore) = engineCore.renderingDispatcher()

  @Provides
  @Physics
  fun providePhysicsDispatcher(engineCore: EngineCore) = engineCore.physicsDispatcher()
}
