package io.jackbradshaw.omnixr.manifest.encoder

import io.jackbradshaw.omnixr.model.InteractionProfile
import io.jackbradshaw.omnixr.model.Input
import io.jackbradshaw.omnixr.model.Output

interface ManifestEncoder {
  fun encodeInput(profile: InteractionProfile, input: Input): String?
  fun decodeInput(encoded: String): Pair<InteractionProfile, Input>?
  fun encodeOutput(profile: InteractionProfile, output: Output): String?
  fun decodeOutput(encoded: String): Pair<InteractionProfile, Output>?
}