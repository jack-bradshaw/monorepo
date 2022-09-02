package io.jackbradshaw.otter.openxr.constants

import java.io.File

object OpenXrConstants {
  val ACTION_MANIFEST_DIRECTORY = File(System.getProperty("java.io.tempdir"))
  val ACTION_MANIFEST_FILENAME = "io_jackbradshaw_otter_openxr_action_manifest.json"
  val ACTION_MANIFEST_FILE = File(ACTION_MANIFEST_DIRECTORY.path + File.separator + ACTION_MANIFEST_FILENAME)
}