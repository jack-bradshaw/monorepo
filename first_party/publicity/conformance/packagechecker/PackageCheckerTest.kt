package com.jackbradshaw.publicity.conformance.packagechecker

import com.google.common.truth.Truth.assertThat
import com.jackbradshaw.publicity.conformance.model.Workspace
import java.io.File
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder

/** Abstract test that all instances of [PackageChecker] should pass. */
abstract class PackageCheckerTest {

  /** Ephemeral directory representing a Bazel workspace root. */
  @get:Rule val workspaceRoot = TemporaryFolder()

  /** The directory of a package in [workspaceRoot]. */
  private lateinit var packageDir: File

  @Before
  fun setUp() {
    workspaceRoot.root.resolve("first_party").mkdirs()
    packageDir = workspaceRoot.newFolder("first_party", "foo")
    val workspace = Workspace(workspaceRoot.root, workspaceRoot.root.resolve("first_party"))
    setupSubject(workspace)
  }

  @Test
  fun public_passes() {
    runSuccessTest(
        """
        load("//first_party/publicity:defs.bzl", "public")
        PUBLICITY = public()
        """
            .trimIndent())
  }

  @Test
  fun internal_passes() {
    runSuccessTest(
        """
        load("//first_party/publicity:defs.bzl", "internal")
        PUBLICITY = internal()
        """
            .trimIndent())
  }

  @Test
  fun restricted_passes() {
    runSuccessTest(
        """
        load("//first_party/publicity:defs.bzl", "restricted")
        PUBLICITY = restricted(["foo"])
        """
            .trimIndent())
  }

  @Test
  fun quarantined_pathMatchesEnclosingPackage_passes() {
    runSuccessTest(
        """
        load("//first_party/publicity:defs.bzl", "quarantined")
        PUBLICITY = quarantined("//first_party/foo")
        """
            .trimIndent())
  }

  @Test
  fun quarantined_pathDoesNotMatchEnclosingPackage_fails() {
    runFailureTest(
        """
        load("//first_party/publicity:defs.bzl", "quarantined")
        PUBLICITY = quarantined("//first_party/bar")
        """
            .trimIndent(),
        "quarantined() in ${packageDir.path}/publicity.bzl must be passed the enclosing package ('//first_party/foo') but '//first_party/bar' was found.")
  }

  @Test
  fun aliasedLoad_passes() {
    runSuccessTest(
        """
        load("//first_party/publicity:defs.bzl", my_pub = "public")
        PUBLICITY = my_pub()
        """
            .trimIndent())
  }

  @Test
  fun noPublicityDeclaration_fails() {
    runFailureTest(
        """
        load("//first_party/publicity:defs.bzl", "public")
        # Missing PUBLICITY assignment
        """
            .trimIndent(),
        "${packageDir.path}/publicity.bzl must contain a variable named PUBLICITY, but none exists")
  }

  @Test
  fun nonFunctionCallAssignment_fails() {
    runFailureTest(
        """
        load("//first_party/publicity:defs.bzl", "public")
        PUBLICITY = "some_string"
        """
            .trimIndent(),
        "PUBLICITY in ${packageDir.path}/publicity.bzl must be assigned a function call, but found \"some_string\".")
  }

  @Test
  fun unsupportedFunctionCall_fails() {
    runFailureTest(
        "PUBLICITY = unknown_func()",
        "PUBLICITY in ${packageDir.path}/publicity.bzl must be assigned a direct call to `public`, `internal`, `restricted` or `quarantined` (or an equivalent load alias), but found 'unknown_func'.")
  }

  @Test
  fun indirectFunctionCall_fails() {
    runFailureTest(
        """
        load("//first_party/publicity:defs.bzl", "public")
        # Dot notation
        PUBLICITY = some_mod.public()
        """
            .trimIndent(),
        "PUBLICITY in ${packageDir.path}/publicity.bzl must be assigned a direct function call, but found some_mod.public.")
  }

  @Test
  fun publicityFileDoesNotExist_fails() {
    // No file population since it does not exist.

    val result = subject().validate(packageDir)

    result.assertFailure(
        "${packageDir.path} must contain a file named publicity.bzl, but none exists")
  }

  /** Configures the [subject]. Should be called exactly once per test. */
  protected abstract fun setupSubject(workspace: Workspace)

  /**
   * Gets the subject under test.
   *
   * Must return the same object on each call (within a single test run). Should only be called
   * after [setupSubject].
   */
  protected abstract fun subject(): PackageChecker

  /**
   * Writes [publicityFileContent] to the publicity file in [packageDir], runs the validation check,
   * and asserts success.
   */
  private fun runSuccessTest(publicityFileContent: String) {
    File(packageDir, "publicity.bzl").writeText(publicityFileContent)

    val result = subject().validate(packageDir)

    result.assertSuccess()
  }

  /**
   * Writes [publicityFileContent] to the publicity file in [packageDir], runs the validation check,
   * and asserts failure with [failureMessage].
   */
  private fun runFailureTest(publicityFileContent: String, failureMessage: String) {
    File(packageDir, "publicity.bzl").writeText(publicityFileContent)

    val result = subject().validate(packageDir)

    result.assertFailure(failureMessage)
  }

  /** Asserts that this [Result] is Successful. */
  private fun PackageChecker.Result.assertSuccess() {
    assertThat(this).isInstanceOf(PackageChecker.Result.Success::class.java)
  }

  /** Asserts that this [Result] is a Failure with [expectedMessage]. */
  private fun PackageChecker.Result.assertFailure(expectedMessage: String) {
    assertThat(this).isInstanceOf(PackageChecker.Result.Failure::class.java)
    val failure = this as PackageChecker.Result.Failure
    assertThat(failure.message).isEqualTo(expectedMessage)
  }
}
