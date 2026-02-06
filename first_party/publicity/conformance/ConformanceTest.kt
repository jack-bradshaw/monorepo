package com.jackbradshaw.publicity.conformance

import com.jackbradshaw.publicity.conformance.packagechecker.PackageCheckerImplModule
import com.jackbradshaw.publicity.conformance.workspacechecker.WorkspaceChecker
import com.jackbradshaw.publicity.conformance.workspacechecker.WorkspaceCheckerImplModule
import dagger.BindsInstance
import dagger.Component
import java.io.File
import org.junit.Before
import org.junit.Test

/**
 * Production entry point for the repository-wide publicity conformance test.
 *
 * The test delegates repository scanning to the [WorkspaceChecker] and is configured by the
 * `BUILD_WORKSPACE_DIRECTORY` and `first_party_root` system properties.
 */
class ConformanceTest {

  private lateinit var workspaceChecker: WorkspaceChecker

  @Before
  fun setup() {
    val firstPartyRoot = System.getProperty("FIRST_PARTY_ROOT") ?: "//first_party"

    workspaceChecker =
        DaggerConformanceComponent.factory()
            .create(findWorkspaceRoot(), firstPartyRoot)
            .workspaceChecker()
  }

  @Test
  fun check() {
    workspaceChecker.checkAllFirstPartyProperties()
  }

  private fun findWorkspaceRoot(): File {
    val rootPath =
        checkNotNull(
            System.getProperty("BUILD_WORKSPACE_DIRECTORY")
                ?: System.getenv("BUILD_WORKSPACE_DIRECTORY")) {
              "BUILD_WORKSPACE_DIRECTORY could not be resolved."
            }

    return File(rootPath).also {
      require(it.exists()) { "Workspace root could not be found: $rootPath" }
      require(it.isDirectory) { "Workspace root must be a directory: $rootPath" }
    }
  }
}

/** Top-level component for the publicity conformance system. */
@ConformanceScope
@Component(
    modules =
        [
            PackageCheckerImplModule::class,
            WorkspaceCheckerImplModule::class,
        ])
internal interface ConformanceComponent {
  fun workspaceChecker(): WorkspaceChecker

  @Component.Factory
  interface Factory {
    fun create(
        @BindsInstance @WorkspaceRoot workspaceRoot: File,
        @BindsInstance @FirstPartyRoot firstPartyRoot: String
    ): ConformanceComponent
  }
}
