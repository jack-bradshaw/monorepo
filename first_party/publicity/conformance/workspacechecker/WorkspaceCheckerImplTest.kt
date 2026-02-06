package com.jackbradshaw.publicity.conformance.workspacechecker

import com.jackbradshaw.publicity.conformance.ConformanceScope
import com.jackbradshaw.publicity.conformance.FirstPartyRoot
import com.jackbradshaw.publicity.conformance.WorkspaceRoot
import com.jackbradshaw.publicity.conformance.packagechecker.PackageCheckerImplModule
import dagger.BindsInstance
import dagger.Component
import java.io.File

/** Tests for [WorkspaceCheckerImpl]. */
class WorkspaceCheckerImplTest : WorkspaceCheckerTest() {

  private lateinit var workspaceChecker: WorkspaceChecker

  override fun setupSubject(workspaceRoot: File, firstPartyRoot: String) {
    workspaceChecker =
        DaggerWorkspaceCheckerImplTest_WorkspaceCheckerTestComponent.factory()
            .create(workspaceRoot, firstPartyRoot)
            .checker()
  }

  override fun subject() = workspaceChecker

  @ConformanceScope
  @Component(
      modules =
          [
              WorkspaceCheckerImplModule::class,
              PackageCheckerImplModule::class,
          ])
  internal interface WorkspaceCheckerTestComponent {
    fun checker(): WorkspaceChecker

    @Component.Factory
    interface Factory {
      fun create(
          @BindsInstance @WorkspaceRoot workspaceRoot: File,
          @BindsInstance @FirstPartyRoot firstPartyRoot: String
      ): WorkspaceCheckerTestComponent
    }
  }
}
