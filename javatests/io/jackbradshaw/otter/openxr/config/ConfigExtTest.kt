package io.jackbradshaw.otter.openxr.config

import com.google.common.truth.Truth.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ConfigExtTest {
  @Test
  fun defaultConfig_usesTempDirectoryForActionManifestDirectory() {
    assertThat(defaultConfig)
        .isEqualTo(
            Config.newBuilder()
                .setActionManifestDirectory(System.getProperty("java.io.tmpdir"))
                .setActionManifestFilename("otter_action_manifest.json")
                .setActionSetName("main")
                .build())
  }
}
