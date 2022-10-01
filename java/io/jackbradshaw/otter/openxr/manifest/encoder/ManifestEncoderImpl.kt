package io.jackbradshaw.otter.openxr.manifest.encoder

import io.jackbradshaw.otter.OtterScope
import io.jackbradshaw.otter.openxr.model.Input
import io.jackbradshaw.otter.openxr.model.InteractionProfile
import io.jackbradshaw.otter.openxr.model.Output
import io.jackbradshaw.otter.openxr.standard.StandardInteractionProfile
import javax.inject.Inject

@OtterScope
class ManifestEncoderImpl @Inject internal constructor() : ManifestEncoder {

  private val inputEncoding: Map<Pair<InteractionProfile, Input>, String> = buildInputEncoding()
  private val inputEncodingReverse = inputEncoding.map { it.value to it.key }.toMap()
  private val outputEncoding: Map<Pair<InteractionProfile, Output>, String> = buildOutputEncoding()
  private val outputEncodingReverse = outputEncoding.map { it.value to it.key }.toMap()

  override fun encodeInput(profile: InteractionProfile, input: Input) = inputEncoding[Pair(profile, input)]
  override fun decodeInput(encoded: String) = inputEncodingReverse[encoded]
  override fun encodeOutput(profile: InteractionProfile, output: Output) = outputEncoding[Pair(profile, output)]
  override fun decodeOutput(encoded: String) = outputEncodingReverse[encoded]

  private fun buildInputEncoding(): Map<Pair<InteractionProfile, Input>, String> {
    var counter = 0
    val map = mutableMapOf<Pair<InteractionProfile, Input>, String>()
    for (profile in StandardInteractionProfile.values().sorted()) {
      val interactionProfile = profile.profile
      for (input in interactionProfile.inputList) {
        map[Pair(interactionProfile, input)] = counter++.toString()
      }
    }
    return map
  }

  private fun buildOutputEncoding(): Map<Pair<InteractionProfile, Output>, String> {
    var counter = 0
    val map = mutableMapOf<Pair<InteractionProfile, Output>, String>()
    for (profile in StandardInteractionProfile.values().sorted()) {
      val interactionProfile = profile.profile
      for (output in interactionProfile.outputList) {
        map[Pair(interactionProfile, output)] = counter++.toString()
      }
    }
    return map
  }
}