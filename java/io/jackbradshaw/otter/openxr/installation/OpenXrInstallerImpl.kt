package io.jackbradshaw.otter.openxr.installation

import io.jackbradshaw.otter.openxr.constants.OpenXrConstants

import io.jackbradshaw.otter.openxr.encoding.Encoding
import io.jackbradshaw.otter.openxr.model.InputIdentifier
import io.jackbradshaw.otter.openxr.model.InputLocation
import io.jackbradshaw.otter.openxr.model.OutputIdentifier
import io.jackbradshaw.otter.openxr.model.OutputLocation
import io.jackbradshaw.otter.openxr.model.InputComponent
import io.jackbradshaw.otter.openxr.model.User
import io.jackbradshaw.otter.openxr.model.Input
import io.jackbradshaw.otter.openxr.standard.StandardInputComponent
import io.jackbradshaw.otter.openxr.model.InteractionProfile
import io.jackbradshaw.otter.openxr.standard.StandardInteractionProfile
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.io.IOException
import java.nio.file.Path
import java.nio.file.Paths
import kotlinx.coroutines.flow.Flow
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

class OpenXrInstallerImpl(
    private val encoding: Encoding
) : OpenXrInstaller {

  override fun deployActionManifestFiles() = runBlocking {
    //OpenXrConstants.ACTION_MANIFEST_FILE.overwriteWith(createPrimaryManifest().toString())
   /* StandardInteractionProfile
        .values()
        .asFlow()
        .map { it.interactionProfile }
        .onEach {
          val file = Paths.get(
              OpenXrConstants.ACTION_MANIFEST_DIRECTORY.toString(),
              it.filename()
          ).toFile()
          val contents = it.createSecondaryManifest()
          file.overwriteWith(contents.toString())
        }.collect()*/

    println("jackbradshaw:\n" + createPrimaryManifest())
  }

  private suspend fun createPrimaryManifest(): JsonObject {
    val profiles: JsonArray = StandardInteractionProfile
        .values()
        .asFlow()
        .map { it.interactionProfile.toInteractionProfileDeclaration() }
        .toJsonArray()

    val actions: JsonArray = StandardInteractionProfile
        .values()
        .asFlow()
        .flatMapConcat { it.interactionProfile.toActionDeclarations() }
        .toJsonArray()

    val actionSet = JsonArray().apply { add(omnisetDeclaration()) }

    return JsonObject().apply {
      add("default_bindings", profiles)
      add("actions", actions)
      add("action_sets", actionSet)
    }
  }

  private suspend fun InteractionProfile.createSecondaryManifest() = JsonObject().apply {
    // put("interaction_profile", path())
    // put("bindings", inputs.toBindings())
  }

  private fun InteractionProfile.filename(): String = "${vendor.standardName}_${controller.standardName}.json"

  private fun InteractionProfile.toInteractionProfileDeclaration(): JsonObject = JsonObject().apply {
    addProperty("binding_url", filename())
    addProperty("controller_type", controller.standardName)
  }

  private fun InteractionProfile.toActionDeclarations() = inputList.asFlow().map { action -> JsonObject().apply {
      addProperty("name", encoding.encodeInput(this@toActionDeclarations, action))
      addProperty("type", action.toType())
    }
  }

  private fun omnisetDeclaration() = JsonObject().apply {
    addProperty("name", OpenXrConstants.ACTION_SET_NAME)
    addProperty("localizedName", OpenXrConstants.ACTION_SET_NAME)
  }

  private fun File.overwriteWith(contents: String) {
    try {
      if (exists()) delete()
      createNewFile()
      BufferedWriter(FileWriter(this)).use {
        it.write(contents)
      }
    } catch (e: IOException) {
      throw IllegalStateException("Failed to overwrite file $this.", e)
    }
  }

  private fun Input.toType(): String = when(StandardInputComponent.fromInputComponent(this.component)) {
    StandardInputComponent.CLICK -> "click"
    StandardInputComponent.TOUCH -> "touch"
    StandardInputComponent.FORCE -> "force"
    StandardInputComponent.VALUE -> "value"
    StandardInputComponent.X -> "x"
    StandardInputComponent.Y -> "y"
    StandardInputComponent.TWIST -> "twist"
    StandardInputComponent.POSE -> "pose"
    null -> throw IllegalStateException("Non-standard input component found: $this")
  }

  private suspend fun Flow<JsonObject>.toJsonArray() = fold(JsonArray()) { array, next -> array.also { it.add(next) } }
}

