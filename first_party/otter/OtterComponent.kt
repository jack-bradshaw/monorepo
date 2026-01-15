package com.jackbradshaw.otter

import com.jackbradshaw.otter.config.Config
import com.jackbradshaw.otter.engine.core.EngineCore
import com.jackbradshaw.otter.openxr.manifest.encoder.ManifestEncoder
import com.jackbradshaw.otter.openxr.manifest.generator.ManifestGenerator
import com.jackbradshaw.otter.openxr.manifest.installer.ManifestInstaller
import com.jackbradshaw.otter.qualifiers.Physics
import com.jackbradshaw.otter.qualifiers.Rendering
import com.jackbradshaw.otter.scene.stage.SceneStage
import com.jackbradshaw.otter.timing.Clock
import kotlinx.coroutines.CoroutineDispatcher

interface OtterComponent {

  @Physics fun physicsCoroutineDispatcher(): CoroutineDispatcher

  @Rendering fun renderingCoroutineDispatcher(): CoroutineDispatcher

  @Physics fun physicsClock(): Clock

  @Rendering fun renderingClock(): Clock

  fun engineCore(): EngineCore

  fun manifestGenerator(): ManifestGenerator

  fun manifestInstaller(): ManifestInstaller

  fun manifestEncoder(): ManifestEncoder

  fun sceneStage(): SceneStage

  fun config(): Config
}
