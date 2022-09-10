package io.jackbradshaw.omnixr.manifest.generator

import io.jackbradshaw.omnixr.model.Input
import io.jackbradshaw.omnixr.standard.StandardInputComponent
import io.jackbradshaw.omnixr.model.InteractionProfile
import io.jackbradshaw.omnixr.manifest.encoder.ManifestEncoder
import io.jackbradshaw.omnixr.standard.StandardInteractionProfile
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.flow.fold
import kotlinx.coroutines.flow.flatMapConcat
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import io.jackbradshaw.omnixr.OmniXrScope
import javax.inject.Inject

@OmniXrScope
class ManifestGeneratorImpl @Inject internal constructor(
    private val encoder: ManifestEncoder
) : ManifestGenerator {

  override suspend fun generateManifests() = Manifests(createPrimaryManifest(), createSecondaryManifests())

  private suspend fun createPrimaryManifest(): String {
    val profileBindings: JsonArray = StandardInteractionProfile
        .values()
        .asFlow()
        .map { it.interactionProfile.declaration() }
        .toJsonArray()

    val actions: JsonArray = StandardInteractionProfile
        .values()
        .asFlow()
        .flatMapConcat { it.interactionProfile.actionDeclarations() }
        .toJsonArray()

    val actionSets = JsonArray().apply { add(actionSetDeclaration()) }

    return JsonObject().apply {
      add("default_bindings", profileBindings)
      add("actions", actions)
      add("action_sets", actionSets)
    }.toString()
  }

  private suspend fun createSecondaryManifests(): Set<SecondaryManifest> = StandardInteractionProfile
      .values()
      .asFlow()
      .map {it.interactionProfile}
      .map { SecondaryManifest(it, "${it.path()}.json", it.toSecondaryManifest()) }
      .toList()
      .toSet()

  private fun InteractionProfile.declaration(): JsonObject = JsonObject().apply {
    addProperty("binding_url", "${path()}.json")
    addProperty("controller_type", "${vendor.id}_${controller.id}")
  }

  private fun InteractionProfile.actionDeclarations(): Flow<JsonObject> = inputList
      .asFlow()
      .map { input ->
        JsonObject().apply {
          addProperty("name", encoder.encodeInput(this@actionDeclarations, input))
          addProperty("type", input.toType())
        }
      }

  private fun actionSetDeclaration() = JsonObject().apply {
    addProperty("name", ACTION_SET_NAME)
    addProperty("usage", "hidden")
    addProperty("localizedName", ACTION_SET_NAME)
  }

  private suspend fun InteractionProfile.toSecondaryManifest(): String = JsonObject().apply {
    addProperty("interaction_profile", path())
    add("bindings", JsonObject().apply {
      add(ACTION_SET_NAME, JsonObject().apply {
        add("sources", JsonArray().apply {
          for (input in inputList) add(input.toBinding(this@toSecondaryManifest))
        })
      })
    })
  }.toString()

  private fun InteractionProfile.path(): String = "${vendor.id}_${controller.id}"

  private fun Input.toType(): String = when (StandardInputComponent.fromInputComponent(this.component)) {
    StandardInputComponent.CLICK -> "boolean"
    StandardInputComponent.TOUCH -> "boolean"
    StandardInputComponent.FORCE -> "vector1"
    StandardInputComponent.VALUE -> "vector1"
    StandardInputComponent.X -> "vector1"
    StandardInputComponent.Y -> "vector1"
    StandardInputComponent.TWIST -> "vector1"
    StandardInputComponent.POSE -> "pose"
    null -> throw IllegalStateException("Non-standard input component found: $this")
  }

  private fun Input.toBinding(profile: InteractionProfile): JsonObject = JsonObject().apply {
    add("inputs", JsonObject().apply {
      add(component.id, JsonObject().apply {
        addProperty("output", encoder.encodeInput(profile, this@toBinding))
      })
    })
    addProperty("path", "/${user.id}/input/${identifier.id}")
  }

  private suspend fun Flow<JsonObject>.toJsonArray() = fold(JsonArray()) { array, next -> array.also { it.add(next) } }

  companion object {
    private val ACTION_SET_NAME = "omniset"
  }
}