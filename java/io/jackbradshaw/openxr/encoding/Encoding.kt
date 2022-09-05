package io.jackbradshaw.otter.openxr.encoding

import io.jackbradshaw.otter.openxr.model.InteractionProfile
import io.jackbradshaw.otter.openxr.model.Input
import io.jackbradshaw.otter.openxr.model.Output

interface Encoding {
  fun encodeInput(profile: InteractionProfile, input: Input): String
  fun decodeInput(encoded: String): Pair<InteractionProfile, Input>
  fun encodeOutput(profile: InteractionProfile, output: Output): String
  fun decodeOutput(encoded: String): Pair<InteractionProfile, Output>
}