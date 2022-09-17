package io.jackbradshaw.clearxr.manifest.encoder

import io.jackbradshaw.clearxr.model.InteractionProfile
import io.jackbradshaw.clearxr.model.Input
import io.jackbradshaw.clearxr.model.Output

interface ManifestEncoder {
  fun encodeInput(profile: InteractionProfile, input: Input): String?
  fun decodeInput(encoded: String): Pair<InteractionProfile, Input>?
  fun encodeOutput(profile: InteractionProfile, output: Output): String?
  fun decodeOutput(encoded: String): Pair<InteractionProfile, Output>?
}