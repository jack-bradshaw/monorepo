package com.jackbradshaw.publicity.conformance.workspacechecker

import com.jackbradshaw.publicity.conformance.model.Workspace
import com.jackbradshaw.publicity.conformance.packagechecker.PackageChecker
import javax.inject.Inject

/** Implementation of [WorkspaceChecker] that uses [PackageChecker]. */
class WorkspaceCheckerImpl @Inject constructor(private val packageChecker: PackageChecker) :
    WorkspaceChecker {

  override fun checkAllFirstPartyProperties(workspace: Workspace): WorkspaceChecker.Result {
    val conformanceErrors = mutableMapOf<String, String>()

    for ((packageIdentifier, packageLocation) in workspace.getFirstPartyProperties()) {
      val result = packageChecker.validate(packageLocation)
      if (result is PackageChecker.Result.Failure) {
        conformanceErrors[packageIdentifier] = result.message
      }
    }

    return if (conformanceErrors.isEmpty()) {
      WorkspaceChecker.Result.AllConform
    } else {
      WorkspaceChecker.Result.NonConformingPropertiesFound(conformanceErrors)
    }
  }
}
