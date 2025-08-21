package com.jackbradshaw.otter.openxr.manifest.encoder

import com.jackbradshaw.otter.openxr.model.Input
import com.jackbradshaw.otter.openxr.model.InteractionProfile
import com.jackbradshaw.otter.openxr.model.Output

interface ManifestEncoder {
  fun encodeInput(profile: InteractionProfile, input: Input): String?

  fun decodeInput(encoded: String): Pair<InteractionProfile, Input>?

  fun encodeOutput(profile: InteractionProfile, output: Output): String?

  fun decodeOutput(encoded: String): Pair<InteractionProfile, Output>?
}
