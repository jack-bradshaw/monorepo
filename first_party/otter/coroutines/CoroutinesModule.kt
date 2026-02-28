package com.jackbradshaw.otter.coroutines

import com.jackbradshaw.otter.engine.core.EngineCore
import com.jackbradshaw.otter.qualifiers.Physics
import com.jackbradshaw.otter.qualifiers.Rendering
import dagger.Module
import dagger.Provides

@Module
object CoroutinesModule {
  @Provides
  @Rendering
  fun provideRenderingDispatcher(engineCore: EngineCore) = engineCore.renderingDispatcher()

  @Provides
  @Physics
  fun providePhysicsDispatcher(engineCore: EngineCore) = engineCore.physicsDispatcher()
}
