package io.jackbradshaw.otter

import dagger.BindsInstance
import dagger.Component
import io.jackbradshaw.otter.config.Config
import io.jackbradshaw.otter.config.defaultConfig
import io.jackbradshaw.otter.coroutines.CoroutinesModule
import io.jackbradshaw.otter.engine.core.EngineCore
import io.jackbradshaw.otter.scene.stage.SceneStageModule
import io.jackbradshaw.otter.engine.core.EngineCoreModule
import io.jackbradshaw.otter.openxr.manifest.encoder.ManifestEncoder
import io.jackbradshaw.otter.openxr.manifest.encoder.ManifestEncoderModule
import io.jackbradshaw.otter.openxr.manifest.generator.ManifestGenerator
import io.jackbradshaw.otter.openxr.manifest.generator.ManifestGeneratorModule
import io.jackbradshaw.otter.openxr.manifest.installer.ManifestInstaller
import io.jackbradshaw.otter.openxr.manifest.installer.ManifestInstallerModule
import io.jackbradshaw.otter.qualifiers.Physics
import io.jackbradshaw.otter.qualifiers.Rendering
import io.jackbradshaw.otter.scene.stage.SceneStage
<<<<<<< HEAD
=======
import io.jackbradshaw.otter.scene.stage.SceneStageModule
>>>>>>> 780513c7d14aae85c67b233f1c2667ee1e78f25b
import io.jackbradshaw.otter.timing.Clock
import io.jackbradshaw.otter.timing.TimingModule
import kotlinx.coroutines.CoroutineDispatcher

@OtterScope
@Component(
    modules =
<<<<<<< HEAD
    [
      EngineCoreModule::class,
      TimingModule::class,
      ManifestInstallerModule::class,
      ManifestGeneratorModule::class,
      ManifestEncoderModule::class,
      CoroutinesModule::class,
      SceneStageModule::class])
=======
        [
            EngineCoreModule::class,
            TimingModule::class,
            ManifestInstallerModule::class,
            ManifestGeneratorModule::class,
            ManifestEncoderModule::class,
            CoroutinesModule::class,
            SceneStageModule::class])
>>>>>>> 780513c7d14aae85c67b233f1c2667ee1e78f25b
interface OtterComponent {

  @Physics
  fun physicsDispatcher(): CoroutineDispatcher

  @Rendering
  fun renderingDispatcher(): CoroutineDispatcher

  @Physics
  fun physicsClock(): Clock

<<<<<<< HEAD
  @Rendering
  fun renderingClock(): Clock
=======
  @Rendering fun renderingClock(): Clock
>>>>>>> 780513c7d14aae85c67b233f1c2667ee1e78f25b

  fun engine(): EngineCore

  fun openXrManifestGenerator(): ManifestGenerator

  fun openXrManifestInstaller(): ManifestInstaller

  fun openXrManifestEncoder(): ManifestEncoder

  fun stage(): SceneStage

  fun config(): Config

  @Component.Builder
  interface Builder {
<<<<<<< HEAD
    @BindsInstance
    fun setConfig(config: Config): Builder
=======
    @BindsInstance fun setConfig(config: Config): Builder
>>>>>>> 780513c7d14aae85c67b233f1c2667ee1e78f25b
    fun build(): OtterComponent
  }
}

<<<<<<< HEAD
fun otter(config: Config = defaultConfig): OtterComponent = DaggerOtterComponent.builder().setConfig(config).build()
=======
fun otter(config: Config = defaultConfig): OtterComponent =
    DaggerOtterComponent.builder().setConfig(config).build()
>>>>>>> 780513c7d14aae85c67b233f1c2667ee1e78f25b
