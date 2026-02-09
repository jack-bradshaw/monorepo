package com.jackbradshaw.publicity.conformance

import com.google.common.truth.Truth.assertThat
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.PrintStream
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder

/**
 * Tests for [Conformance].
 *
 * These test check whether the confornance test is properly invoked by the JVM, with emphasis on
 * system integration, not the individaul conformance rules. As such, the cases only cover two
 * scenarios: A conformant workspace and a non-conformant workspace. Various other tests check the
 * conformance rules more closely.
 */
class ConformanceTest {

  @get:Rule val workspaceRoot = TemporaryFolder()

  private val originalOutStream = System.out
  private val originalErrStream = System.err
  private val testOutStream = ByteArrayOutputStream()
  private val testErrorStream = ByteArrayOutputStream()

  private lateinit var firstPartyRoot: File

  @Before
  fun setup() {
    System.setOut(PrintStream(testOutStream))
    System.setErr(PrintStream(testErrorStream))
    System.setProperty("BUILD_WORKSPACE_DIRECTORY", workspaceRoot.root.absolutePath)
    System.setProperty("FIRST_PARTY_ROOT", "first_party")

    firstPartyRoot = workspaceRoot.root.resolve("first_party")
  }

  @After
  fun tearDown() {
    System.setOut(originalOutStream)
    System.setErr(originalErrStream)
    System.clearProperty("BUILD_WORKSPACE_DIRECTORY")
    System.clearProperty("FIRST_PARTY_ROOT")
  }

  @Test
  fun run_conformantWorkspace_returnsZero() {
    createConformantPackage("pkg1")

    val exitCode = Conformance.run(stdout = testOutStream, stderr = testErrorStream)

    assertThat(exitCode).isEqualTo(0)
    assertThat(testOutStream.toString()).contains("All first-party properties are conformant.")
  }

  @Test
  fun run_nonConformantWorkspace_returnsOneAndPrintsError() {
    createNonConformantPackage("pkg1")

    val exitCode = Conformance.run(stdout = testOutStream, stderr = testErrorStream)

    val pkg1Path = workspaceRoot.root.resolve("first_party").resolve("pkg1").absolutePath
    assertThat(exitCode).isEqualTo(1)
    assertThat(testErrorStream.toString())
        .isEqualTo(
            "Non-conforming properties found:${System.lineSeparator()}" +
                "//first_party/pkg1: $pkg1Path must contain a file named publicity.bzl, " +
                "but none exists${System.lineSeparator()}")
  }

  /** Creates a package under the first-party root that should pass all conformance checks. */
  private fun createConformantPackage(name: String) {
    val packageDir = firstPartyRoot.resolve(name).also { it.mkdirs() }

    File(packageDir, "publicity.bzl")
        .writeText(
            """
              load("//first_party/publicity:defs.bzl", "public")
              PUBLICITY = public()
            """
                .trimIndent())
  }

  /**
   * Creates a package under the first party root that will fail conformance checks by virtue of
   * missing a publicity.bzl file.
   */
  private fun createNonConformantPackage(name: String) {
    firstPartyRoot.resolve(name).mkdirs()
  }
}
