package io.jackbradshaw.omnixr.encoding

import io.jackbradshaw.omnixr.model.InteractionProfile
import io.jackbradshaw.omnixr.model.Input
import io.jackbradshaw.omnixr.model.Output

interface Encoding {
  fun encodeInput(profile: InteractionProfile, input: Input): String
  fun decodeInput(encoded: String): Pair<InteractionProfile, Input>
  fun encodeOutput(profile: InteractionProfile, output: Output): String
  fun decodeOutput(encoded: String): Pair<InteractionProfile, Output>
}