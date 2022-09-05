package io.jackbradshaw.openxr

import io.jackbradshaw.openxr.encoding.Encoding
import io.jackbradshaw.openxr.manifest.ManifestGenerator
import dagger.Component
import dagger.BindsInstance
import io.jackbradshaw.openxr.encoding.EncodingModule
import io.jackbradshaw.openxr.installation.OpenXrInstallerModule
import io.jackbradshaw.openxr.installation.OpenXrInstaller
import io.jackbradshaw.openxr.manifest.ManifestGeneratorModule
import io.jackbradshaw.openxr.config.Config

@OpenXrScope
@Component(modules = [EncodingModule::class, OpenXrInstallerModule::class, ManifestGeneratorModule::class])
interface OpenXrComponent {

  fun encoding(): Encoding
  fun installer(): OpenXrInstaller
  fun manifestGenerator(): ManifestGenerator

  @Component.Builder
  interface Builder {
    @BindsInstance
    fun setConfig(config: Config): Builder
    fun build(): OpenXrComponent
  }
}