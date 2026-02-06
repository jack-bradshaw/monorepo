package com.jackbradshaw.publicity.conformance.workspacechecker

import com.google.common.truth.Truth.assertThat
import java.io.File
import kotlin.test.assertFailsWith
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder

/** Abstract test that all instances of [WorkspaceChecker] should pass. */
abstract class WorkspaceCheckerTest {

  @get:Rule val workspaceRoot = TemporaryFolder()

  @Test
  fun check_noPackages_passes() {
    setupSubject(workspaceRoot.root, "//first_party")
    workspaceRoot.root.resolve("first_party").mkdirs()
    subject().checkAllFirstPartyProperties()
  }

  @Test
  fun check_firstPartyRootDoesNotExist_fails() {
    setupSubject(workspaceRoot.root, "//nonexistent")

    val exception =
        assertFailsWith<IllegalArgumentException> { subject().checkAllFirstPartyProperties() }

    assertThat(exception).hasMessageThat().contains("First-party root does not exist")
  }

  @Test
  fun check_firstPartyRootIsFile_fails() {
    workspaceRoot.newFile("first_party_file")
    setupSubject(workspaceRoot.root, "//first_party_file")

    val exception =
        assertFailsWith<IllegalArgumentException> { subject().checkAllFirstPartyProperties() }

    assertThat(exception).hasMessageThat().contains("First-party root is not a directory")
  }

  @Test
  fun check_onePackage_conformant_passes() {
    setupSubject(workspaceRoot.root, "//first_party")
    createConformantPackage("pkg1")
    subject().checkAllFirstPartyProperties()
  }

  @Test
  fun check_onePackage_missingPublicity_fails() {
    setupSubject(workspaceRoot.root, "//first_party")
    createPackageWithNoDeclaration("pkg1")

    val exception = assertFailsWith<AssertionError> { subject().checkAllFirstPartyProperties() }

    assertThat(exception).hasMessageThat().contains("Expected a publicity.bzl file")
  }

  @Test
  fun check_onePackage_invalidDeclaration_fails() {
    setupSubject(workspaceRoot.root, "//first_party")
    createPackageWithInvalidDeclaration("pkg1")

    val exception = assertFailsWith<AssertionError> { subject().checkAllFirstPartyProperties() }

    assertThat(exception).hasMessageThat().contains("must contain a variable named PUBLICITY")
  }

  @Test
  fun check_multiplePackagesAllConformant_passes() {
    setupSubject(workspaceRoot.root, "//first_party")
    createConformantPackage("pkg1")
    createConformantPackage("pkg2")
    createConformantPackage("pkg3")
    subject().checkAllFirstPartyProperties()
  }

  @Test
  fun check_multiplePackages_oneNonConformant_fails() {
    setupSubject(workspaceRoot.root, "//first_party")
    createConformantPackage("pkg1")
    createConformantPackage("pkg2")
    createPackageWithNoDeclaration("pkg3")

    val exception = assertFailsWith<AssertionError> { subject().checkAllFirstPartyProperties() }

    assertThat(exception).hasMessageThat().contains("Expected a publicity.bzl file")
  }

  @Test
  fun check_multiplePackages_allNonConformant_fails() {
    setupSubject(workspaceRoot.root, "//first_party")
    createPackageWithNoDeclaration("pkg1")
    createPackageWithNoDeclaration("pkg2")

    val exception = assertFailsWith<AssertionError> { subject().checkAllFirstPartyProperties() }

    assertThat(exception).hasMessageThat().contains("Expected a publicity.bzl file")
  }

  /**
   * Returns the subjct under test. Must be ready for testing. Must return the same object on each
   * call (within a single test run).
   */
  protected abstract fun subject(): WorkspaceChecker

  /** Configures the [subject]. Should be called exactly once per test. */
  protected abstract fun setupSubject(workspaceRoot: File, firstPartyRoot: String)

  protected fun createConformantPackage(name: String, root: String = "first_party") {
    val packageDir = workspaceRoot.root.resolve(root).resolve(name).also { it.mkdirs() }

    File(packageDir, "publicity.bzl")
        .writeText(
            """
      load("//first_party/publicity:defs.bzl", "public")
      PUBLICITY = public()
    """
                .trimIndent())
  }

  protected fun createPackageWithNoDeclaration(name: String, root: String = "first_party") {
    workspaceRoot.root.resolve(root).resolve(name).mkdirs()
  }

  protected fun createPackageWithInvalidDeclaration(name: String, root: String = "first_party") {
    val packageDir = workspaceRoot.root.resolve(root).resolve(name).also { it.mkdirs() }
    File(packageDir, "publicity.bzl").writeText("FOO = 'bar'")
  }
}
