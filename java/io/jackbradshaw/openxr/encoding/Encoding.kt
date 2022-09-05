package io.jackbradshaw.openxr.encoding

import io.jackbradshaw.openxr.model.InteractionProfile
import io.jackbradshaw.openxr.model.Input
import io.jackbradshaw.openxr.model.Output

interface Encoding {
  fun encodeInput(profile: InteractionProfile, input: Input): String
  fun decodeInput(encoded: String): Pair<InteractionProfile, Input>
  fun encodeOutput(profile: InteractionProfile, output: Output): String
  fun decodeOutput(encoded: String): Pair<InteractionProfile, Output>
}