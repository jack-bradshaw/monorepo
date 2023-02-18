package io.jackbradshaw.otter.openxr.manifest.generator

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import io.jackbradshaw.otter.OtterScope
import io.jackbradshaw.otter.config.Config
import io.jackbradshaw.otter.openxr.manifest.encoder.ManifestEncoder
import io.jackbradshaw.otter.openxr.model.Input
import io.jackbradshaw.otter.openxr.model.InteractionProfile
import io.jackbradshaw.otter.openxr.standard.StandardInputComponent
import io.jackbradshaw.otter.openxr.standard.StandardInteractionProfile
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.fold
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList

@OtterScope
class ManifestGeneratorImpl
@Inject
internal constructor(private val config: Config, private val encoder: ManifestEncoder) :
    ManifestGenerator {

  override suspend fun generateManifests() =
      Manifests(createPrimaryManifest(), createSecondaryManifests())

  private suspend fun createPrimaryManifest(): String {
    val profileBindings: JsonArray =
        StandardInteractionProfile.values().asFlow().map { it.profile.declaration() }.toJsonArray()

    val actions: JsonArray =
        StandardInteractionProfile.values()
            .asFlow()
            .flatMapConcat { it.profile.actionDeclarations() }
            .toJsonArray()

    val actionSets = JsonArray().apply { add(actionSetDeclaration()) }

    return JsonObject()
        .apply {
          add("default_bindings", profileBindings)
          add("actions", actions)
          add("action_sets", actionSets)
        }
        .toString()
  }

  private suspend fun createSecondaryManifests(): Set<SecondaryManifest> =
      StandardInteractionProfile.values()
          .asFlow()
          .map { it.profile }
          .map { SecondaryManifest(it, "${it.path()}.json", it.toSecondaryManifest()) }
          .toList()
          .toSet()

  private fun InteractionProfile.declaration(): JsonObject =
      JsonObject().apply {
        addProperty("binding_url", "${path()}.json")
        addProperty("controller_type", "${vendor.id}_${controller.id}")
      }

  private fun InteractionProfile.actionDeclarations(): Flow<JsonObject> =
      inputList.asFlow().map { input ->
        JsonObject().apply {
          addProperty("name", encoder.encodeInput(this@actionDeclarations, input))
          addProperty("type", input.toType())
        }
      }

  private fun actionSetDeclaration() =
      JsonObject().apply {
        addProperty("name", config.openXrConfig.actionSetName)
        addProperty("usage", "hidden")
        addProperty("localizedName", config.openXrConfig.actionSetName)
      }

  private suspend fun InteractionProfile.toSecondaryManifest(): String =
      JsonObject()
          .apply {
            addProperty("interaction_profile", path())
            add(
                "bindings",
                JsonObject().apply {
                  add(
                      config.openXrConfig.actionSetName,
                      JsonObject().apply {
                        add(
                            "sources",
                            JsonArray().apply {
                              for (input in inputList) add(
                                  input.toBinding(this@toSecondaryManifest))
                            })
                      })
                })
          }
          .toString()

  private fun InteractionProfile.path(): String = "${vendor.id}_${controller.id}"

  private fun Input.toType(): String =
      when (StandardInputComponent.fromInputComponent(this.component)) {
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

  private fun Input.toBinding(profile: InteractionProfile): JsonObject =
      JsonObject().apply {
        add(
            "inputs",
            JsonObject().apply {
              add(
                  component.id,
                  JsonObject().apply {
                    addProperty("output", encoder.encodeInput(profile, this@toBinding))
                  })
            })
        addProperty("path", "/${user.id}/input/${identifier.id}")
      }

  private suspend fun Flow<JsonObject>.toJsonArray() =
      fold(JsonArray()) { array, next -> array.also { it.add(next) } }
}
