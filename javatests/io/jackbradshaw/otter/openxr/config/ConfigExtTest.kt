package io.jackbradshaw.otter.openxr.config

import com.google.common.truth.Truth.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import kotlinx.coroutines.runBlocking

@RunWith(JUnit4::class)
class ConfigExtTest {
  @Test
  fun defaultConfig_usesTempDirectoryForActionManifestDirectory() {
    assertThat(defaultConfig).isEqualTo(config {
      actionManifestDirectory = System.getProperty("java.io.tmpdir")
      actionManifestFilename = "otter_action_manifest.json"
      actionSetName = "main"
    })
  }
}