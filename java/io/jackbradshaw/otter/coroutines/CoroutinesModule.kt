package io.jackbradshaw.otter.coroutines

import dagger.Module
import dagger.Provides
import io.jackbradshaw.otter.engine.core.EngineCore
import io.jackbradshaw.otter.qualifiers.Rendering
import io.jackbradshaw.otter.qualifiers.Physics

@Module
object CoroutinesModule {
  @Provides @Rendering fun provideRenderingDispatcher(engineCore: EngineCore) = engineCore.renderingDispatcher()
  @Provides @Physics fun providePhysicsDispatcher(engineCore: EngineCore) = engineCore.physicsDispatcher()
}
