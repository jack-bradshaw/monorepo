package io.jackbradshaw.omnixr

import io.jackbradshaw.omnixr.encoding.Encoding
import io.jackbradshaw.omnixr.manifest.ManifestGenerator
import dagger.Component
import dagger.BindsInstance
import io.jackbradshaw.omnixr.encoding.EncodingModule
import io.jackbradshaw.omnixr.installation.OpenXrInstallerModule
import io.jackbradshaw.omnixr.installation.OpenXrInstaller
import io.jackbradshaw.omnixr.manifest.ManifestGeneratorModule
import io.jackbradshaw.omnixr.config.Config

@OmniXrScope
@Component(modules = [EncodingModule::class, OpenXrInstallerModule::class, ManifestGeneratorModule::class])
interface OmniXrComponent {

  fun encoding(): Encoding
  fun installer(): OpenXrInstaller
  fun manifestGenerator(): ManifestGenerator

  @Component.Builder
  interface Builder {
    @BindsInstance
    fun setConfig(config: Config): Builder
    fun build(): OmniXrComponent
  }
}