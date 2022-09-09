package io.jackbradshaw.omnixr.config

import com.google.common.truth.Truth.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import kotlinx.coroutines.runBlocking

@RunWith(JUnit4::class)
class ConfigExtTest {

  @Test
  fun config_createsConfigWithPassedValues() {
    val directory = "testdirectory"
    val filename = "testfilename"

    val config = config(directory, filename)

    assertThat(config).isEqualTo(
        Config.newBuilder().setActionManifestDirectory(directory).setActionManifestFilename(filename).build()
    )
  }

  @Test
  fun defaultConfig_usesTempDirectoryForActionManifestDirectory() {
    assertThat(defaultConfig).isEqualTo(
        Config
            .newBuilder()
            .setActionManifestDirectory(System.getProperty("java.io.tmpdir"))
            .setActionManifestFilename("omnixr_action_manifest.json")
            .build()
    )
  }
}