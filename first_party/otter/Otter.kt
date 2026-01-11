package com.jackbradshaw.otter

import com.jackbradshaw.otter.config.Config
import com.jackbradshaw.otter.config.defaultConfig
import com.jackbradshaw.otter.coroutines.CoroutinesModule
import com.jackbradshaw.otter.engine.core.EngineCore
import com.jackbradshaw.otter.engine.core.EngineCoreModule
import com.jackbradshaw.otter.openxr.manifest.encoder.ManifestEncoder
import com.jackbradshaw.otter.openxr.manifest.encoder.ManifestEncoderModule
import com.jackbradshaw.otter.openxr.manifest.generator.ManifestGenerator
import com.jackbradshaw.otter.openxr.manifest.generator.ManifestGeneratorModule
import com.jackbradshaw.otter.openxr.manifest.installer.ManifestInstaller
import com.jackbradshaw.otter.openxr.manifest.installer.ManifestInstallerModule
import com.jackbradshaw.otter.qualifiers.Physics
import com.jackbradshaw.otter.qualifiers.Rendering
import com.jackbradshaw.otter.scene.stage.SceneStage
import com.jackbradshaw.otter.scene.stage.SceneStageModule
import com.jackbradshaw.otter.timing.Clock
import com.jackbradshaw.otter.timing.TimingModule
import dagger.BindsInstance
import dagger.Component
import kotlinx.coroutines.CoroutineDispatcher

interface OtterComponent {

  @Physics fun physicsDispatcher(): CoroutineDispatcher

  @Rendering fun renderingDispatcher(): CoroutineDispatcher

  @Physics fun physicsClock(): Clock

  @Rendering fun renderingClock(): Clock

  fun engine(): EngineCore

  fun openXrManifestGenerator(): ManifestGenerator

  fun openXrManifestInstaller(): ManifestInstaller

  fun openXrManifestEncoder(): ManifestEncoder

  fun stage(): SceneStage

  fun config(): Config
}

@OtterScope
@Component(
    modules =
        [
            EngineCoreModule::class,
            TimingModule::class,
            ManifestInstallerModule::class,
            ManifestGeneratorModule::class,
            ManifestEncoderModule::class,
            CoroutinesModule::class,
            SceneStageModule::class])
interface ProdOtterComponent : OtterComponent {
  @Component.Builder
  interface Builder {

    @BindsInstance fun binding(config: Config): Builder

    fun build(): ProdOtterComponent
  }
}

fun otter(config: Config = defaultConfig): OtterComponent =
    DaggerProdOtterComponent.builder().binding(config).build()
