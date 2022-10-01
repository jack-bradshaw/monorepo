package io.jackbradshaw.otter.openxr.manifest.generator

import io.jackbradshaw.otter.openxr.model.InteractionProfile

interface ManifestGenerator {
  suspend fun generateManifests(): Manifests
}

/**
 * A collection of OpenXR action manifest files.
 *
 * The [primaryManifest] declares the profiles, actions and action sets. Each of the
 * [secondaryManifests] entries associates an interaction profile with a binding manifest that maps
 * raw inputs to actions.
 */
data class Manifests(val primaryManifest: String, val secondaryManifests: Set<SecondaryManifest>)

data class SecondaryManifest(val profile: InteractionProfile, val url: String, val content: String)
