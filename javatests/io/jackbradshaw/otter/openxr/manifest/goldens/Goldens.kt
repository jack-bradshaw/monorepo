package io.jackbradshaw.otter.openxr.manifest.goldens

import com.google.devtools.build.runfiles.Runfiles
import io.jackbradshaw.otter.openxr.standard.StandardInteractionProfile
import io.jackbradshaw.otter.openxr.standard.StandardInteractionProfile.GOOGLE_DAYDREAM_CONTROLLER
import io.jackbradshaw.otter.openxr.standard.StandardInteractionProfile.HTC_VIVE_CONTROLLER
import io.jackbradshaw.otter.openxr.standard.StandardInteractionProfile.HTC_VIVE_PRO
import io.jackbradshaw.otter.openxr.standard.StandardInteractionProfile.KHRONOS_SIMPLE_CONTROLLER
import io.jackbradshaw.otter.openxr.standard.StandardInteractionProfile.MICROSOFT_MIXED_REALITY_MOTION_CONTROLLER
import io.jackbradshaw.otter.openxr.standard.StandardInteractionProfile.MICROSOFT_XBOX_CONTROLLER
import io.jackbradshaw.otter.openxr.standard.StandardInteractionProfile.OCCULUS_GO_CONTROLLER
import io.jackbradshaw.otter.openxr.standard.StandardInteractionProfile.OCCULUS_TOUCH_CONTROLLER
import io.jackbradshaw.otter.openxr.standard.StandardInteractionProfile.VALVE_INDEX_CONTROLLER
import java.io.File

val goldenPrimaryManifest = readGoldenFile("primary_manifest.json")
val goldenSecondaryManifests =
    buildMap<StandardInteractionProfile, String> {
      put(KHRONOS_SIMPLE_CONTROLLER, readGoldenFile("khr_simple_controller.json"))
      put(GOOGLE_DAYDREAM_CONTROLLER, readGoldenFile("google_daydream_controller.json"))
      put(HTC_VIVE_CONTROLLER, readGoldenFile("htc_vive_controller.json"))
      put(HTC_VIVE_PRO, readGoldenFile("htc_vive_pro.json"))
      put(
          MICROSOFT_MIXED_REALITY_MOTION_CONTROLLER,
          readGoldenFile("microsoft_motion_controller.json"))
      put(MICROSOFT_XBOX_CONTROLLER, readGoldenFile("microsoft_xbox_controller.json"))
      put(OCCULUS_GO_CONTROLLER, readGoldenFile("occulus_go_controller.json"))
      put(OCCULUS_TOUCH_CONTROLLER, readGoldenFile("occulus_touch_controller.json"))
      put(VALVE_INDEX_CONTROLLER, readGoldenFile("valve_index_controller.json"))
    }

private fun readGoldenFile(filename: String) =
    File(
            Runfiles.create()
                .rlocation(
                    "io_jackbradshaw/javatests/io/jackbradshaw/otter/openxr/manifest/goldens/$filename"))
        .readText()
