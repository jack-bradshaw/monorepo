package io.jackbradshaw.clearxr

import dagger.Component
import dagger.BindsInstance
import io.jackbradshaw.clearxr.manifest.encoder.ManifestEncoder
import io.jackbradshaw.clearxr.manifest.encoder.ManifestEncoderModule
import io.jackbradshaw.clearxr.manifest.installer.ManifestInstaller
import io.jackbradshaw.clearxr.manifest.installer.ManifestInstallerModule
import io.jackbradshaw.clearxr.manifest.generator.ManifestGenerator
import io.jackbradshaw.clearxr.manifest.generator.ManifestGeneratorModule
import io.jackbradshaw.clearxr.config.Config
import io.jackbradshaw.clearxr.config.defaultConfig

@clearxrScope
@Component(modules = [ManifestEncoderModule::class, ManifestInstallerModule::class, ManifestGeneratorModule::class])
interface ClearXr {

  fun config(): Config
  fun manifestEncoder(): ManifestEncoder
  fun manifestInstaller(): ManifestInstaller
  fun manifestGenerator(): ManifestGenerator

  @Component.Builder
  interface Builder {
    @BindsInstance
    fun setConfig(config: Config): Builder
    fun build(): ClearXr
  }
}

fun clearxr(config: Config = defaultConfig): ClearXr = DaggerClearXr.builder().setConfig(config).build()