package io.jackbradshaw.openxr.encoding

import io.jackbradshaw.openxr.OpenXrScope
import io.jackbradshaw.openxr.model.InteractionProfile
import io.jackbradshaw.openxr.standard.StandardInteractionProfile
import io.jackbradshaw.openxr.model.Input
import io.jackbradshaw.openxr.model.Output
import javax.inject.Inject

@OpenXrScope
class EncodingImpl @Inject internal constructor() : Encoding {

  private val inputEncoding: Map<Pair<InteractionProfile, Input>, String> = buildInputEncoding()
  private val inputEncodingReverse = inputEncoding.map { it.value to it.key }.toMap()
  private val outputEncoding: Map<Pair<InteractionProfile, Output>, String> = buildOutputEncoding()
  private val outputEncodingReverse = outputEncoding.map { it.value to it.key }.toMap()

  override fun encodeInput(profile: InteractionProfile, input: Input) = inputEncoding[Pair(profile, input)]!!
  override fun decodeInput(encoded: String) = inputEncodingReverse[encoded]!!
  override fun encodeOutput(profile: InteractionProfile, output: Output) = outputEncoding[Pair(profile, output)]!!
  override fun decodeOutput(encoded: String): Pair<InteractionProfile, Output> = outputEncodingReverse[encoded]!!

  private fun buildInputEncoding(): Map<Pair<InteractionProfile, Input>, String> {
    var counter = 0;
    val map = mutableMapOf<Pair<InteractionProfile, Input>, String>()
    for (profile in StandardInteractionProfile.values()) {
      val interactionProfile = profile.interactionProfile
      for (input in interactionProfile.inputList) {
        map[Pair(interactionProfile, input)] = counter++.toString()
      }
    }
    return map
  }

  private fun buildOutputEncoding(): Map<Pair<InteractionProfile, Output>, String> {
    var counter = 0;
    val map = mutableMapOf<Pair<InteractionProfile, Output>, String>()
    for (profile in StandardInteractionProfile.values()) {
      val interactionProfile = profile.interactionProfile
      for (output in interactionProfile.outputList) {
        map[Pair(interactionProfile, output)] = counter++.toString()
      }
    }
    return map
  }
}