package com.jackbradshaw.otter.openxr.manifest.installer

import com.google.common.truth.Truth.assertThat
import com.jackbradshaw.otter.OtterComponent
import com.jackbradshaw.otter.config.Config
import com.jackbradshaw.otter.openxr.manifest.generator.ManifestGenerator
import com.jackbradshaw.otter.openxr.manifest.goldens.goldenPrimaryManifest
import com.jackbradshaw.otter.openxr.manifest.goldens.goldenSecondaryManifests
import com.jackbradshaw.otter.openxr.model.InteractionProfile
import com.jackbradshaw.otter.openxr.standard.StandardInteractionProfile
import com.jackbradshaw.otter.otter
import dagger.Component
import java.io.File
import javax.inject.Inject
import javax.inject.Scope
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ManifestInstallerTest {

  @Inject lateinit var config: Config
  @Inject lateinit var generator: ManifestGenerator
  @Inject lateinit var installer: ManifestInstaller

  @Before
  fun setUp() {
    DaggerTestComponent.builder().setOtter(otter()).build().inject(this)
  }

  @Test
  fun deployActionManifestFiles_deploysPrimaryManifest() = runBlocking {
    installer.deployActionManifestFiles()

    val fileContents: String =
        File(
                config.openXrConfig.actionManifestDirectory,
                config.openXrConfig.actionManifestFilename)
            .bufferedReader()
            .readLines()[0]

    assertThat(fileContents).isEqualTo(goldenPrimaryManifest)
  }

  @Test
  fun deployActionManifestFiles_deploysSecondaryManifests() = runBlocking {
    installer.deployActionManifestFiles()
    val secondaryManifests = generator.generateManifests().secondaryManifests

    for (profile in StandardInteractionProfile.values()) {
      val filename = profile.profile.expectedSecondaryManifestFilename()
      val contents = File(config.openXrConfig.actionManifestDirectory, filename).readText()

      assertThat(contents).isEqualTo(goldenSecondaryManifests[profile])
    }
  }

  private fun InteractionProfile.expectedSecondaryManifestFilename() =
      vendor.id + "_" + controller.id + ".json"
}

@Scope @Retention(AnnotationRetention.RUNTIME) annotation class TestScope

@TestScope
@Component(dependencies = [OtterComponent::class])
interface TestComponent {
  fun inject(test: ManifestInstallerTest)

  @Component.Builder
  interface Builder {
    fun setOtter(otter: OtterComponent): Builder

    fun build(): TestComponent
  }
}
