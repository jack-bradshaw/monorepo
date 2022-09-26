package io.jackbradshaw.otter.openxr.config

val defaultConfig = config {
  actionManifestDirectory = System.getProperty("java.io.tmpdir")
  actionManifestFilename = "otter_action_manifest.json"
  actionSetName = "main"
}