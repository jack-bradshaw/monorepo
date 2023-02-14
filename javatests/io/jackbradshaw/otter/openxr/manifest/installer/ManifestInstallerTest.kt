package io.jackbradshaw.otter.openxr.manifest.installer

import com.google.common.truth.Truth.assertThat
import io.jackbradshaw.otter.openxr.manifest.generator.ManifestGenerator
import io.jackbradshaw.otter.openxr.standard.StandardInteractionProfile
import io.jackbradshaw.otter.openxr.model.InteractionProfile
import io.jackbradshaw.otter.config.Config
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import kotlinx.coroutines.runBlocking
import io.jackbradshaw.otter.OtterComponent
import io.jackbradshaw.otter.otter
import java.io.File
import io.jackbradshaw.otter.openxr.manifest.goldens.goldenPrimaryManifest
import io.jackbradshaw.otter.openxr.manifest.goldens.goldenSecondaryManifests
import dagger.Component
import javax.inject.Scope
import javax.inject.Inject

@RunWith(JUnit4::class)
class ManifestInstallerTest {

  @Inject
  lateinit var config: Config
  @Inject
  lateinit var generator: ManifestGenerator
  @Inject
  lateinit var installer: ManifestInstaller

  @Before
  fun setUp() {
    DaggerTestComponent.builder().setOtter(otter()).build().inject(this)
  }

  @Test
  fun deployActionManifestFiles_deploysPrimaryManifest() = runBlocking {
    installer.deployActionManifestFiles()

    val fileContents: String = File(
        config.openXrConfig.actionManifestDirectory,
        config.openXrConfig.actionManifestFilename
    ).bufferedReader().readLines()[0]

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

  private fun InteractionProfile.expectedSecondaryManifestFilename() = vendor.id + "_" + controller.id + ".json"
}

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class TestScope

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