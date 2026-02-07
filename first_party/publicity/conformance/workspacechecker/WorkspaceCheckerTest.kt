package com.jackbradshaw.publicity.conformance.workspacechecker

import com.google.common.truth.Truth.assertThat
import com.jackbradshaw.publicity.conformance.model.Workspace
import java.io.File
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder

/** Abstract test that all instances of [WorkspaceChecker] should pass. */
abstract class WorkspaceCheckerTest {

  @get:Rule val workspaceRoot = TemporaryFolder()

  private lateinit var workspace: Workspace

  @Before
  fun setup() {
    workspace = createWorkspace()
    setupSubject(workspace)
  }

  @Test
  fun noPackages_passes() {
    val result = subject().checkAllFirstPartyProperties(workspace)

    assertThat(result).isEqualTo(WorkspaceChecker.Result.AllConform)
  }

  @Test
  fun onePackage_conformant_passes() {
    createConformantPackage("pkg1", workspace)

    val result = subject().checkAllFirstPartyProperties(workspace)

    assertThat(result).isEqualTo(WorkspaceChecker.Result.AllConform)
  }

  @Test
  fun onePackage_missingPublicity_fails() {
    createPackageWithNoDeclaration("pkg1", workspace)

    val result = subject().checkAllFirstPartyProperties(workspace)

    assertThat(result)
        .isInstanceOf(WorkspaceChecker.Result.NonConformingPropertiesFound::class.java)
    val failure = result as WorkspaceChecker.Result.NonConformingPropertiesFound
    assertThat(failure.properties)
        .containsExactlyEntriesIn(
            mapOf(
                "//first_party/pkg1" to
                    "${workspace.workspaceRoot.path}/first_party/pkg1 must contain a file named publicity.bzl, but none exists"))
  }

  @Test
  fun onePackage_invalidDeclaration_fails() {
    createPackageWithInvalidDeclaration("pkg1", workspace)

    val result = subject().checkAllFirstPartyProperties(workspace)

    assertThat(result)
        .isInstanceOf(WorkspaceChecker.Result.NonConformingPropertiesFound::class.java)
    val failure = result as WorkspaceChecker.Result.NonConformingPropertiesFound
    assertThat(failure.properties)
        .containsExactlyEntriesIn(
            mapOf(
                "//first_party/pkg1" to
                    "${workspace.workspaceRoot.path}/first_party/pkg1/publicity.bzl must contain a variable named PUBLICITY, but none exists"))
  }

  @Test
  fun multiplePackagesAllConformant_passes() {
    createConformantPackage("pkg1", workspace)
    createConformantPackage("pkg2", workspace)
    createConformantPackage("pkg3", workspace)

    val result = subject().checkAllFirstPartyProperties(workspace)

    assertThat(result).isEqualTo(WorkspaceChecker.Result.AllConform)
  }

  @Test
  fun multiplePackages_oneNonConformant_fails() {
    createConformantPackage("pkg1", workspace)
    createConformantPackage("pkg2", workspace)
    createPackageWithNoDeclaration("pkg3", workspace)

    val result = subject().checkAllFirstPartyProperties(workspace)

    assertThat(result)
        .isInstanceOf(WorkspaceChecker.Result.NonConformingPropertiesFound::class.java)
    val failure = result as WorkspaceChecker.Result.NonConformingPropertiesFound
    assertThat(failure.properties)
        .containsExactlyEntriesIn(
            mapOf(
                "//first_party/pkg3" to
                    "${workspace.workspaceRoot.path}/first_party/pkg3 must contain a file named publicity.bzl, but none exists"))
  }

  @Test
  fun multiplePackages_allNonConformant_fails() {
    createPackageWithNoDeclaration("pkg1", workspace)
    createPackageWithNoDeclaration("pkg2", workspace)

    val result = subject().checkAllFirstPartyProperties(workspace)

    assertThat(result)
        .isInstanceOf(WorkspaceChecker.Result.NonConformingPropertiesFound::class.java)
    val failure = result as WorkspaceChecker.Result.NonConformingPropertiesFound
    assertThat(failure.properties)
        .containsExactlyEntriesIn(
            mapOf(
                "//first_party/pkg1" to
                    "${workspace.workspaceRoot.path}/first_party/pkg1 must contain a file named publicity.bzl, but none exists",
                "//first_party/pkg2" to
                    "${workspace.workspaceRoot.path}/first_party/pkg2 must contain a file named publicity.bzl, but none exists"))
  }

  /** Configures the [subject]. Should be called exactly once per test. */
  protected abstract fun setupSubject(workspace: Workspace)

  /** Creates a workspace with a first party package at //first_party. */
  private fun createWorkspace(): Workspace {
    val firstPartyRoot = workspaceRoot.root.resolve("first_party").also { it.mkdirs() }
    val workspace = Workspace(workspaceRoot.root, firstPartyRoot)
    return workspace
  }

  /**
   * Gets the subject under test.
   *
   * Must return the same object on each call (within a single test run). Should only be called
   * after [setupSubject].
   */
  protected abstract fun subject(): WorkspaceChecker

  private fun createConformantPackage(name: String, workspace: Workspace) {
    val packageDir = workspace.firstPartyRoot.resolve(name).also { it.mkdirs() }

    File(packageDir, "publicity.bzl")
        .writeText(
            """
            load("//first_party/publicity:defs.bzl", "public")
            PUBLICITY = public()
            """
                .trimIndent())
  }

  private fun createPackageWithNoDeclaration(name: String, workspace: Workspace) {
    workspace.firstPartyRoot.resolve(name).mkdirs()
  }

  private fun createPackageWithInvalidDeclaration(name: String, workspace: Workspace) {
    val packageDir = workspace.firstPartyRoot.resolve(name).also { it.mkdirs() }
    File(packageDir, "publicity.bzl").writeText("FOO = 'bar'")
  }
}
