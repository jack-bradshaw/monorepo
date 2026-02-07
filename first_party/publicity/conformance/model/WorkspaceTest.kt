package com.jackbradshaw.publicity.conformance.model

import com.google.common.truth.Truth.assertThat
import kotlin.test.assertFailsWith
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder

/** Unit tests for [Workspace]. */
class WorkspaceTest {

  @get:Rule val tempFolder = TemporaryFolder()

  @Test
  fun init_validWorkspace_succeeds() {
    val workspaceRoot = tempFolder.newFolder("workspace")
    val firstPartyRoot = tempFolder.newFolder("workspace", "first_party")

    Workspace(workspaceRoot, firstPartyRoot)
  }

  @Test
  fun init_workspaceRootDoesNotExist_fails() {
    val workspaceRoot = tempFolder.root.resolve("nonexistent")
    val firstPartyRoot = tempFolder.root.resolve("nonexistent/first_party")

    val exception =
        assertFailsWith<IllegalArgumentException> { Workspace(workspaceRoot, firstPartyRoot) }
    assertThat(exception).hasMessageThat().isEqualTo("Workspace root '$workspaceRoot' must exist.")
  }

  @Test
  fun init_workspaceRootIsFile_fails() {
    val workspaceRoot = tempFolder.newFile("workspace_file")
    val firstPartyRoot = tempFolder.root.resolve("first_party")

    val exception =
        assertFailsWith<IllegalArgumentException> { Workspace(workspaceRoot, firstPartyRoot) }
    assertThat(exception)
        .hasMessageThat()
        .isEqualTo("Workspace root '$workspaceRoot' must be a directory.")
  }

  @Test
  fun init_firstPartyRootDoesNotExist_fails() {
    val workspaceRoot = tempFolder.newFolder("workspace")
    val firstPartyRoot = workspaceRoot.resolve("nonexistent")

    val exception =
        assertFailsWith<IllegalArgumentException> { Workspace(workspaceRoot, firstPartyRoot) }
    assertThat(exception)
        .hasMessageThat()
        .isEqualTo("First-party root '$firstPartyRoot' must exist.")
  }

  @Test
  fun init_firstPartyRootIsFile_fails() {
    val workspaceRoot = tempFolder.newFolder("workspace")
    val firstPartyRoot = workspaceRoot.resolve("first_party_file").also { it.createNewFile() }

    val exception =
        assertFailsWith<IllegalArgumentException> { Workspace(workspaceRoot, firstPartyRoot) }
    assertThat(exception)
        .hasMessageThat()
        .isEqualTo("First-party root '$firstPartyRoot' must be a directory.")
  }

  @Test
  fun init_firstPartyOutsideWorkspace_fails() {
    val workspaceRoot = tempFolder.newFolder("workspace")
    val firstPartyRoot = tempFolder.newFolder("other")

    val exception =
        assertFailsWith<IllegalArgumentException> { Workspace(workspaceRoot, firstPartyRoot) }
    assertThat(exception)
        .hasMessageThat()
        .isEqualTo(
            "First-party root '$firstPartyRoot' must be located within the workspace root '$workspaceRoot'.")
  }

  @Test
  fun identifyPackage_insideWorkspace_returnsIdentifier() {
    val workspaceRoot = tempFolder.newFolder("workspace")
    val firstPartyRoot = workspaceRoot.resolve("first_party").also { it.mkdirs() }
    val workspace = Workspace(workspaceRoot, firstPartyRoot)
    val packageDir = firstPartyRoot.resolve("foo/bar").also { it.mkdirs() }

    val identifier = workspace.identifyPackage(packageDir)

    assertThat(identifier).isEqualTo("//first_party/foo/bar")
  }

  @Test
  fun identifyPackage_outsideWorkspace_fails() {
    val workspaceRoot = tempFolder.newFolder("workspace")
    val firstPartyRoot = workspaceRoot.resolve("first_party").also { it.mkdirs() }
    val workspace = Workspace(workspaceRoot, firstPartyRoot)
    val outsideDir = tempFolder.newFolder("outside")

    val exception =
        assertFailsWith<IllegalArgumentException> { workspace.identifyPackage(outsideDir) }
    assertThat(exception)
        .hasMessageThat()
        .isEqualTo(
            "Directory '$outsideDir' must be located within the workspace root '$workspaceRoot'.")
  }

  @Test
  fun getFirstPartyProperties_empty_returnsEmptyMap() {
    val workspaceRoot = tempFolder.newFolder("workspace")
    val firstPartyRoot = workspaceRoot.resolve("first_party").also { it.mkdirs() }
    val workspace = Workspace(workspaceRoot, firstPartyRoot)

    assertThat(workspace.getFirstPartyProperties()).isEmpty()
  }

  @Test
  fun getFirstPartyProperties_withDirectories_returnsMapWithIdentifiers() {
    val workspaceRoot = tempFolder.newFolder("workspace")
    val firstPartyRoot = workspaceRoot.resolve("first_party").also { it.mkdirs() }
    val workspace = Workspace(workspaceRoot, firstPartyRoot)
    val pkg1 = firstPartyRoot.resolve("pkg1").also { it.mkdirs() }
    val pkg2 = firstPartyRoot.resolve("pkg2").also { it.mkdirs() }

    val properties = workspace.getFirstPartyProperties()

    assertThat(properties)
        .containsExactlyEntriesIn(mapOf("//first_party/pkg1" to pkg1, "//first_party/pkg2" to pkg2))
  }

  @Test
  fun getFirstPartyProperties_withFiles_ignoresFiles() {
    val workspaceRoot = tempFolder.newFolder("workspace")
    val firstPartyRoot = workspaceRoot.resolve("first_party").also { it.mkdirs() }
    val workspace = Workspace(workspaceRoot, firstPartyRoot)
    firstPartyRoot.resolve("file.txt").createNewFile()

    assertThat(workspace.getFirstPartyProperties()).isEmpty()
  }

  @Test
  fun getFirstPartyProperties_mixed_returnsOnlyDirectories() {
    val workspaceRoot = tempFolder.newFolder("workspace")
    val firstPartyRoot = workspaceRoot.resolve("first_party").also { it.mkdirs() }
    val workspace = Workspace(workspaceRoot, firstPartyRoot)
    val pkg1 = firstPartyRoot.resolve("pkg1").also { it.mkdirs() }
    firstPartyRoot.resolve("file.txt").createNewFile()

    val properties = workspace.getFirstPartyProperties()

    assertThat(properties).containsExactlyEntriesIn(mapOf("//first_party/pkg1" to pkg1))
  }
}
