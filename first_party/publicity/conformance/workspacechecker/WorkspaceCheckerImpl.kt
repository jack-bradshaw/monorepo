package com.jackbradshaw.publicity.conformance.workspacechecker

import com.google.common.truth.Truth.assertWithMessage
import com.jackbradshaw.publicity.conformance.FirstPartyRoot
import com.jackbradshaw.publicity.conformance.WorkspaceRoot
import com.jackbradshaw.publicity.conformance.packagechecker.PackageChecker
import java.io.File
import javax.inject.Inject

/** Implementation of [WorkspaceChecker] that uses [PackageChecker]. */
class WorkspaceCheckerImpl
@Inject
constructor(
    @WorkspaceRoot private val workspaceRoot: File,
    @FirstPartyRoot private val firstPartyRoot: String,
    private val packageChecker: PackageChecker
) : WorkspaceChecker {

  override fun checkAllFirstPartyProperties() {
    for (firstPartyProperty in getFirstPartyProperties()) {
      checkExistenceOfPublicityFile(firstPartyProperty)
      checkDeclarationsInPublicityFile(firstPartyProperty)
    }
  }

  private fun checkExistenceOfPublicityFile(firstPartyProperty: File) {
    assertWithMessage(
            "Expected a publicity.bzl file (Pattern B) at ${firstPartyProperty.absolutePath}/publicity.bzl")
        .that(File(firstPartyProperty, "publicity.bzl").exists())
        .isTrue()
  }

  private fun checkDeclarationsInPublicityFile(firstPartyProperty: File) {
    val result = packageChecker.validate(firstPartyProperty)
    if (result is PackageChecker.Result.Failure) {
      assertWithMessage("Conformance failure in ${firstPartyProperty.absolutePath}/publicity.bzl")
          .that(result.message)
          .isNull()
    }
  }

  private fun getFirstPartyProperties(): List<File> {
    val rootRelativePath = firstPartyRoot.removePrefix("//")
    val firstPartyDir = workspaceRoot.resolve(rootRelativePath)

    require(firstPartyDir.exists()) { "First-party root does not exist: $firstPartyRoot" }
    require(firstPartyDir.isDirectory) { "First-party root is not a directory: $firstPartyRoot" }

    return firstPartyDir.listFiles()?.filter { it.isDirectory }?.toList() ?: emptyList()
  }
}
