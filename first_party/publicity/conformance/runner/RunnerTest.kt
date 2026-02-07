package com.jackbradshaw.publicity.conformance.runner

import com.google.common.truth.Truth.assertThat
import com.jackbradshaw.publicity.conformance.model.Workspace
import java.io.ByteArrayOutputStream
import java.io.File
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder

/** Abstract test that all instances of [Runner] should pass. */
abstract class RunnerTest {

  @get:Rule val workspaceRoot = TemporaryFolder()

  private val outStream = ByteArrayOutputStream()
  private val errStream = ByteArrayOutputStream()

  private lateinit var workspace: Workspace

  @Before
  fun setup() {
    val firstPartyRoot = workspaceRoot.root.resolve("first_party").also { it.mkdirs() }
    workspace = Workspace(workspaceRoot.root, firstPartyRoot)
    setupSubject(workspace, outStream, errStream)
  }

  @Test
  fun run_allConformant_returnsZeroAndReportsSuccess() = runBlocking {
    createConformantPackage("pkg1")

    val result = subject().run()

    awaitClosure()

    assertThat(result).isEqualTo(Runner.Result.SUCCESS)
    assertThat(outStream.toString())
        .isEqualTo("All first-party properties are conformant.${System.lineSeparator()}")
  }

  @Test
  fun run_nonConforming_returnsOneAndReportsFailure() = runBlocking {
    createNonConformantPackage("pkg1")

    val result = subject().run()

    awaitClosure()

    assertThat(result).isEqualTo(Runner.Result.FAIL)
    assertThat(errStream.toString())
        .isEqualTo(
            "Non-conforming properties found:${System.lineSeparator()}" +
                "//first_party/pkg1: ${workspaceRoot.root.absolutePath}/first_party/pkg1 must" +
                " contain a file named publicity.bzl, but none exists${System.lineSeparator()}")
  }

  @Test
  fun run_multipleFailures_returnsZeroAndReportsEachFailure() = runBlocking {
    createNonConformantPackage("pkg1")
    createNonConformantPackage("pkg2")

    val result = subject().run()

    awaitClosure()

    assertThat(result).isEqualTo(Runner.Result.FAIL)
    assertThat(errStream.toString())
        .isEqualTo(
            "Non-conforming properties found:${System.lineSeparator()}" +
                "//first_party/pkg1: ${workspaceRoot.root.absolutePath}/first_party/pkg1 must " +
                "contain a file named publicity.bzl, but none exists${System.lineSeparator()}" +
                "//first_party/pkg2: ${workspaceRoot.root.absolutePath}/first_party/pkg2 must " +
                "contain a file named publicity.bzl, but none exists${System.lineSeparator()}")
  }

  /**
   * Gets the subject under test.
   *
   * Must return the same object on each call (within a single test run). Should only be called
   * after [setupSubject].
   */
  protected abstract fun subject(): Runner

  /** Closes any resources opened by the runner during `run` and awaits closure. */
  protected abstract suspend fun awaitClosure()

  /** Configures the [subject]. Should be called exactly once per test. */
  protected abstract fun setupSubject(
      workspace: Workspace,
      out: ByteArrayOutputStream,
      err: ByteArrayOutputStream
  )

  private fun createConformantPackage(name: String) {
    val packageDir = workspace.firstPartyRoot.resolve(name).also { it.mkdirs() }

    File(packageDir, "publicity.bzl")
        .writeText(
            """
      load("//first_party/publicity:defs.bzl", "public")
      PUBLICITY = public()
    """
                .trimIndent())
  }

  private fun createNonConformantPackage(name: String) {
    val packageDir = workspace.firstPartyRoot.resolve(name).also { it.mkdirs() }
  }
}
