package io.jackbradshaw.otter

import dagger.BindsInstance
import dagger.Component
import io.jackbradshaw.otter.clock.Clock
import io.jackbradshaw.otter.clock.ClockModule
import io.jackbradshaw.otter.config.Config
import io.jackbradshaw.otter.config.defaultConfig
import io.jackbradshaw.otter.clock.Physics
import io.jackbradshaw.otter.clock.Rendering
import io.jackbradshaw.otter.clock.Real
import io.jackbradshaw.otter.engine.Engine
import io.jackbradshaw.otter.engine.EngineModule
import io.jackbradshaw.otter.openxr.manifest.encoder.ManifestEncoderModule
import io.jackbradshaw.otter.openxr.manifest.generator.ManifestGeneratorModule
import io.jackbradshaw.otter.openxr.manifest.installer.ManifestInstallerModule

@OtterScope
@Component(
    modules = [
      EngineModule::class,
      ClockModule::class,
      ManifestInstallerModule::class,
      ManifestGeneratorModule::class,
      ManifestEncoderModule::class
    ]
)
interface OtterComponent {

  @Physics
  fun physicsClock(): Clock

  @Rendering
  fun renderingClock(): Clock

  @Real
  fun realClock(): Clock

  fun engine(): Engine

  @Component.Builder
  interface Builder {
    @BindsInstance
    fun setConfig(config: Config): Builder
    fun build(): OtterComponent
  }
}

fun otter(config: Config = defaultConfig): OtterComponent =
    DaggerOtterComponent.builder().setConfig(config).build()