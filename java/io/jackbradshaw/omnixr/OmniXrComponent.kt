package io.jackbradshaw.omnixr

import dagger.Component
import dagger.BindsInstance
import io.jackbradshaw.omnixr.manifest.encoder.ManifestEncoder
import io.jackbradshaw.omnixr.manifest.encoder.ManifestEncoderModule
import io.jackbradshaw.omnixr.manifest.installer.ManifestInstaller
import io.jackbradshaw.omnixr.manifest.installer.ManifestInstallerModule
import io.jackbradshaw.omnixr.manifest.generator.ManifestGenerator
import io.jackbradshaw.omnixr.manifest.generator.ManifestGeneratorModule
import io.jackbradshaw.omnixr.config.Config

@OmniXrScope
@Component(modules = [ManifestEncoderModule::class, ManifestInstallerModule::class, ManifestGeneratorModule::class])
interface OmniXrComponent {

  fun manifestEncoder(): ManifestEncoder
  fun manifestInstaller(): ManifestInstaller
  fun manifestGenerator(): ManifestGenerator

  @Component.Builder
  interface Builder {
    @BindsInstance
    fun setConfig(config: Config): Builder
    fun build(): OmniXrComponent
  }
}