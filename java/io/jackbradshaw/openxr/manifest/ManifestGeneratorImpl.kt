package io.jackbradshaw.otter.openxr.manifest

import io.jackbradshaw.otter.openxr.constants.OpenXrConstants
import io.jackbradshaw.otter.openxr.model.InputIdentifier
import io.jackbradshaw.otter.openxr.model.InputLocation
import io.jackbradshaw.otter.openxr.model.OutputIdentifier
import io.jackbradshaw.otter.openxr.model.OutputLocation
import io.jackbradshaw.otter.openxr.model.InputComponent
import io.jackbradshaw.otter.openxr.model.User
import io.jackbradshaw.otter.openxr.model.Input
import io.jackbradshaw.otter.openxr.standard.StandardInputComponent
import io.jackbradshaw.otter.openxr.model.InteractionProfile
import io.jackbradshaw.otter.openxr.encoding.Encoding
import io.jackbradshaw.otter.openxr.standard.StandardInteractionProfile
import kotlinx.coroutines.flow.Flow
import io.jackbradshaw.klu.flow.toMap
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.fold
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.onEach
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.Gson
import kotlinx.coroutines.runBlocking

// TODO still needs two items of work:
// 2. Secondary manifests and very wrong. Need to work out what's going on there.

class ManifestGeneratorImpl(
    private val encoding: Encoding
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
      .map { SecondaryManifest("${it.path()}.json", it.toSecondaryManifest()) }
      .toList()
      .toSet()

  private fun InteractionProfile.declaration(): JsonObject = JsonObject().apply {
    addProperty("binding_url", "${path()}.json")
    addProperty("controller_type", "${vendor.standardName}_${controller.standardName}")
  }

  private fun InteractionProfile.actionDeclarations(): Flow<JsonObject> = inputList
      .asFlow()
      .map { input ->
        JsonObject().apply {
          addProperty("name", encoding.encodeInput(this@actionDeclarations, input))
          addProperty("type", input.toType())
        }
      }

  private fun actionSetDeclaration() = JsonObject().apply {
    addProperty("name", OpenXrConstants.ACTION_SET_NAME)
    addProperty("usage", "hidden")
    addProperty("localizedName", OpenXrConstants.ACTION_SET_NAME)
  }

  private suspend fun InteractionProfile.toSecondaryManifest(): String = JsonObject().apply {
    addProperty("interaction_profile", path())
    add("bindings", JsonObject().apply {
      add(OpenXrConstants.ACTION_SET_NAME, JsonObject().apply {
        add("sources", JsonArray().apply {
          for (input in inputList) add(input.toBinding(this@toSecondaryManifest))
        })
      })
    })
  }.toString()

  private fun InteractionProfile.path(): String = "${vendor.standardName}_${controller.standardName}"

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
      add(component.standardName, JsonObject().apply {
        addProperty("output", encoding.encodeInput(profile, this@toBinding))
      })
    })
    addProperty("path", "/${user.standardName}/input/${identifier.standardName}")
  }

  private suspend fun Flow<JsonObject>.toJsonArray() = fold(JsonArray()) { array, next -> array.also { it.add(next) } }
}