package io.jackbradshaw.otter.scene.stage

import dagger.Binds
import dagger.Module
import io.jackbradshaw.otter.OtterScope

@Module
interface SceneStageModule {
  @Binds fun bindSceneStage(impl: SceneStageImpl): SceneStage
}