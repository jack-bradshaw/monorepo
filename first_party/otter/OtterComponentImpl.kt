package com.jackbradshaw.otter

import com.jackbradshaw.otter.config.Config
import com.jackbradshaw.otter.config.defaultConfig
import com.jackbradshaw.otter.coroutines.CoroutinesModule
import com.jackbradshaw.otter.engine.core.EngineCoreModule
import com.jackbradshaw.otter.openxr.manifest.encoder.ManifestEncoderModule
import com.jackbradshaw.otter.openxr.manifest.generator.ManifestGeneratorModule
import com.jackbradshaw.otter.openxr.manifest.installer.ManifestInstallerModule
import com.jackbradshaw.otter.scene.stage.SceneStageModule
import com.jackbradshaw.otter.timing.TimingModule
import dagger.BindsInstance
import dagger.Component

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
interface OtterComponentImpl : OtterComponent {
  @Component.Builder
  interface Builder {

    @BindsInstance fun binding(config: Config): Builder

    fun build(): OtterComponentImpl
  }
}

fun otterComponent(config: Config = defaultConfig): OtterComponent =
    DaggerOtterComponentImpl.builder().binding(config).build()
