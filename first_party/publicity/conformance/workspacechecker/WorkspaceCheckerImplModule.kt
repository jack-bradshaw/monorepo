package com.jackbradshaw.publicity.conformance.workspacechecker

import dagger.Binds
import dagger.Module

@Module
interface WorkspaceCheckerImplModule {
  @Binds fun bindWorkspaceChecker(impl: WorkspaceCheckerImpl): WorkspaceChecker
}
