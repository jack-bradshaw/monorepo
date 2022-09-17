package io.jackbradshaw.clearxr.manifest.installer

import com.google.common.truth.Truth.assertThat
import io.jackbradshaw.clearxr.clearxr
import io.jackbradshaw.clearxr.manifest.generator.ManifestGenerator
import io.jackbradshaw.clearxr.standard.StandardInteractionProfile
import io.jackbradshaw.clearxr.model.InteractionProfile
import io.jackbradshaw.clearxr.config.Config
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import kotlinx.coroutines.runBlocking
import java.io.File
import io.jackbradshaw.clearxr.manifest.goldens.goldenPrimaryManifest
import io.jackbradshaw.clearxr.manifest.goldens.goldenSecondaryManifests

@RunWith(JUnit4::class)
class ManifestInstallerTest {

  private lateinit var config: Config
  private lateinit var generator: ManifestGenerator
  private lateinit var installer: ManifestInstaller

  @Before
  fun setUp() {
    clearxr().let {
      config = it.config()
      generator = it.manifestGenerator()
      installer = it.manifestInstaller()
    }
  }

  @Test
  fun deployActionManifestFiles_deploysPrimaryManifest() = runBlocking {
    installer.deployActionManifestFiles()

    val fileContents: String = File(config.actionManifestDirectory, config.actionManifestFilename).bufferedReader().readLines()[0]

    assertThat(fileContents).isEqualTo(goldenPrimaryManifest)
  }

  @Test
  fun deployActionManifestFiles_deploysSecondaryManifests() = runBlocking {
    installer.deployActionManifestFiles()
    val secondaryManifests = generator.generateManifests().secondaryManifests

    for (profile in StandardInteractionProfile.values()) {
      val filename = profile.interactionProfile.expectedSecondaryManifestFilename()
      val contents = File(config.actionManifestDirectory, filename).readText()

      assertThat(contents).isEqualTo(goldenSecondaryManifests[profile])
    }
  }

  private fun InteractionProfile.expectedSecondaryManifestFilename(): String {
    return vendor.standardName + "_" + controller.standardName + ".json"
  }
}