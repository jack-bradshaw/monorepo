package com.jackbradshaw.publicity.conformance.runner

import com.jackbradshaw.publicity.conformance.model.Workspace
import com.jackbradshaw.publicity.conformance.packagechecker.PackageCheckerImplModule
import com.jackbradshaw.publicity.conformance.workspacechecker.WorkspaceCheckerImplModule
import com.jackbradshaw.sasync.standard.StandardComponent
import com.jackbradshaw.sasync.standard.standardComponent
import dagger.BindsInstance
import dagger.Component

@Component(
    dependencies = [StandardComponent::class],
    modules =
        [
            PackageCheckerImplModule::class,
            WorkspaceCheckerImplModule::class,
            RunnerModule::class,
        ])
internal interface RunnerComponentImpl : RunnerComponent {
  @Component.Factory
  interface Factory {
    fun create(
        @BindsInstance workspace: Workspace,
        standardComponent: StandardComponent
    ): RunnerComponentImpl
  }
}

/** Creates a [RunnerComponent]. */
fun runnerComponent(workspace: Workspace, standardComponent: StandardComponent): RunnerComponent =
    DaggerRunnerComponentImpl.factory().create(workspace, standardComponent)
