package com.jackbradshaw.publicity.conformance.workspacechecker

/** Checks whether all first party properties in a workspace are conformant. */
interface WorkspaceChecker {
  /**
   * Scans the workspace and checks all first party properties are conformant. Throws an
   * [AssertionError] when the fitst non-conformant packages is found (if any).
   */
  fun checkAllFirstPartyProperties()
}
