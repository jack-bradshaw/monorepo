package com.jackbradshaw.publicity.conformance.model

import java.io.File

/**
 * A Bazel workspace with a single directory that represents the root of all first party code (i.e.
 * the first party root).
 *
 * This class strictly models filesystem locations without awareness of bazel specific rules (e.g.
 * it does not check for the presence of BUILD files etc.).
 *
 * @property workspaceRoot The absolute path to the root of the Bazel workspace.
 * @property firstPartyRoot The absolute path to the first party root.
 */
data class Workspace(val workspaceRoot: File, val firstPartyRoot: File) {
  init {
    require(workspaceRoot.exists()) { "Workspace root '$workspaceRoot' must exist." }
    require(workspaceRoot.isDirectory) { "Workspace root '$workspaceRoot' must be a directory." }

    require(firstPartyRoot.exists()) { "First-party root '$firstPartyRoot' must exist." }
    require(firstPartyRoot.isDirectory) {
      "First-party root '$firstPartyRoot' must be a directory."
    }

    require(firstPartyRoot.canonicalPath.startsWith(workspaceRoot.canonicalPath)) {
      "First-party root '$firstPartyRoot' must be located within the workspace root '$workspaceRoot'."
    }
  }

  /**
   * Returns the Bazel package identifier of [directory] with respect to this workspace.
   *
   * No check is done to ensure the package contains a BUILD file. The return includes the leading
   * "//" (e.g. `//first_party/foo`).
   *
   * @throws IllegalArgumentException if [directory] is not located within the workspace root.
   */
  fun identifyPackage(directory: File): String {
    require(directory.canonicalPath.startsWith(workspaceRoot.canonicalPath)) {
      "Directory '$directory' must be located within the workspace root '$workspaceRoot'."
    }
    val relativePath = directory.relativeTo(workspaceRoot).path
    return "//$relativePath"
  }

  /**
   * Gets all first party properties as a map from their package identifier to thier location (e.g.
   * //first_party/foo -> /tmp/jack/workspaces/workspace1/first_party/foo).
   */
  fun getFirstPartyProperties(): Map<String, File> {
    return firstPartyRoot
        .listFiles()
        ?.filter { it.isDirectory }
        ?.associate {
          val identifier = identifyPackage(it)
          identifier to it
        } ?: emptyMap()
  }
}
