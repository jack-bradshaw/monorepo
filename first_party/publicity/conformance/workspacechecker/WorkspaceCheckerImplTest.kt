package com.jackbradshaw.publicity.conformance.workspacechecker

import com.jackbradshaw.publicity.conformance.model.Workspace
import com.jackbradshaw.publicity.conformance.packagechecker.PackageCheckerImplModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Inject

/** Tests for [WorkspaceCheckerImpl]. */
class WorkspaceCheckerImplTest : WorkspaceCheckerTest() {

  @Inject lateinit var checker: WorkspaceChecker

  override fun setupSubject(workspace: Workspace) {
    DaggerWorkspaceCheckerImplTest_WorkspaceCheckerTestComponent.factory()
        .create(workspace)
        .inject(this)
  }

  override fun subject() = checker

  @Component(
      modules =
          [
              WorkspaceCheckerImplModule::class,
              PackageCheckerImplModule::class,
          ])
  internal interface WorkspaceCheckerTestComponent {
    fun inject(target: WorkspaceCheckerImplTest)

    @Component.Factory
    interface Factory {
      fun create(@BindsInstance workspace: Workspace): WorkspaceCheckerTestComponent
    }
  }
}
