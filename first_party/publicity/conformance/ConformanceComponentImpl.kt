package com.jackbradshaw.publicity.conformance

import com.jackbradshaw.publicity.conformance.model.Workspace
import com.jackbradshaw.publicity.conformance.packagechecker.PackageCheckerImplModule
import com.jackbradshaw.publicity.conformance.runner.RunnerImplModule
import com.jackbradshaw.publicity.conformance.workspacechecker.WorkspaceCheckerImplModule
import com.jackbradshaw.sasync.standard.StandardComponent
import dagger.BindsInstance
import dagger.Component

@Component(
    dependencies = [StandardComponent::class],
    modules =
        [
            PackageCheckerImplModule::class,
            WorkspaceCheckerImplModule::class,
            RunnerImplModule::class,
        ])
internal interface ConformanceComponentImpl : ConformanceComponent {
  @Component.Factory
  interface Factory {
    fun create(
        @BindsInstance workspace: Workspace,
        standardComponent: StandardComponent
    ): ConformanceComponentImpl
  }
}

/** Creates a [ConformanceComponent]. */
fun conformanceComponent(
    workspace: Workspace,
    standardComponent: StandardComponent
): ConformanceComponent =
    DaggerConformanceComponentImpl.factory().create(workspace, standardComponent)
