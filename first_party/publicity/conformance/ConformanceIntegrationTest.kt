package com.jackbradshaw.publicity.conformance

import com.google.common.truth.Truth.assertThat
import java.io.File
import kotlin.test.assertFailsWith
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder

/**
 * System integration test for the publicity conformance suite.
 *
 * Verifies that the entire system (Macro -> ConformanceTest -> Checker) functions correctly. Works
 * by spoofing the workspace directory and first party package name in envronmental variables then
 * executing the conformancet test manually.
 */
class ConformanceIntegrationTest {

  @get:Rule val workspaceRoot = TemporaryFolder()

  private lateinit var conformanceTest: ConformanceTest

  @Before
  fun setup() {
    System.setProperty("BUILD_WORKSPACE_DIRECTORY", workspaceRoot.root.absolutePath)
    System.setProperty("FIRST_PARTY_ROOT", "//first_party")

    conformanceTest = ConformanceTest().also { it.setup() }
  }

  @Test
  fun checkConformantWorkspace_passes() {
    createConformantPackage("pkg1")

    conformanceTest.check()
  }

  @Test
  fun checkNonConformantWorkspace_fails() {
    createPackageWithNoDeclaration("pkg1")

    val exception = assertFailsWith<AssertionError> { conformanceTest.check() }

    assertThat(exception).hasMessageThat().contains("Expected a publicity.bzl file")
  }

  @Test
  fun customFirstPartyRoot_passes() {
    val customRoot = "//custom"
    System.setProperty("FIRST_PARTY_ROOT", customRoot)
    val customConformanceTest = ConformanceTest().apply { setup() }

    createConformantPackage("pkg1", root = "custom")

    customConformanceTest.check()
  }

  private fun createConformantPackage(name: String, root: String = "first_party") {
    val packageDir = workspaceRoot.root.resolve(root).resolve(name).also { it.mkdirs() }

    File(packageDir, "publicity.bzl")
        .writeText(
            """
      load("//first_party/publicity:defs.bzl", "public")
      PUBLICITY = public()
    """
                .trimIndent())
  }

  private fun createPackageWithNoDeclaration(name: String, root: String = "first_party") {
    workspaceRoot.root.resolve(root).resolve(name).mkdirs()
  }
}
