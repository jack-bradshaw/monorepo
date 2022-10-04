package io.jackbradshaw.otter

import dagger.BindsInstance
import dagger.Component
import io.jackbradshaw.otter.config.Config
import io.jackbradshaw.otter.config.defaultConfig
import io.jackbradshaw.otter.engine.Engine
import io.jackbradshaw.otter.engine.EngineModule
import io.jackbradshaw.otter.openxr.manifest.encoder.ManifestEncoder
import io.jackbradshaw.otter.openxr.manifest.encoder.ManifestEncoderModule
import io.jackbradshaw.otter.openxr.manifest.generator.ManifestGenerator
import io.jackbradshaw.otter.openxr.manifest.generator.ManifestGeneratorModule
import io.jackbradshaw.otter.openxr.manifest.installer.ManifestInstaller
import io.jackbradshaw.otter.openxr.manifest.installer.ManifestInstallerModule
import io.jackbradshaw.otter.qualifiers.Rendering
import io.jackbradshaw.otter.qualifiers.Physics
import io.jackbradshaw.otter.qualifiers.Real
import io.jackbradshaw.otter.timing.Clock
import io.jackbradshaw.otter.timing.TimingModule

@OtterScope
@Component(
    modules =
        [
            EngineModule::class,
            TimingModule::class,
            ManifestInstallerModule::class,
            ManifestGeneratorModule::class,
            ManifestEncoderModule::class])
interface Otter {

  @Physics fun physicsClock(): Clock

  @Rendering fun renderingClock(): Clock

  @Real fun realClock(): Clock

  fun engine(): Engine

  fun manifestGenerator(): ManifestGenerator
  fun manifestInstaller(): ManifestInstaller
  fun manifestEncoder(): ManifestEncoder
  fun config(): Config

  @Component.Builder
  interface Builder {
    @BindsInstance fun setConfig(config: Config): Builder
    fun build(): Otter
  }
}

fun otter(config: Config = defaultConfig): Otter = DaggerOtter.builder().setConfig(config).build()
