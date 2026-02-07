package com.jackbradshaw.publicity.conformance.workspacechecker

import com.jackbradshaw.publicity.conformance.model.Workspace

/** Checks whether all first party properties in a workspace are conformant. */
interface WorkspaceChecker {
  /**
   * Checks all first party properties in [workspace] for conformance and returns the result. All
   * are checked even if some are non-conformant, and the result details all non-conformant (if
   * any).
   */
  fun checkAllFirstPartyProperties(workspace: Workspace): Result

  /** The result of a call to [checkAllFirstPartyProperties]. */
  sealed class Result {

    /** All first party properties in the workspace are conformant. */
    object AllConform : Result()

    /**
     * At least one first party property in the workspace does not conform. The result maps the path
     * of each property to the error in that property.
     */
    class NonConformingPropertiesFound(val properties: Map<String, String>) : Result()
  }
}
