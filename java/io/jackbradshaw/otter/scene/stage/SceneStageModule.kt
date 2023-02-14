package io.jackbradshaw.otter.scene.stage

import dagger.Binds
import dagger.Module

@Module
interface SceneStageModule {
  @Binds fun bindSceneStage(impl: SceneStageImpl): SceneStage
}
