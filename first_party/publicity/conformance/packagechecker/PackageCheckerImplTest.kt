package com.jackbradshaw.publicity.conformance.packagechecker

import com.jackbradshaw.publicity.conformance.model.Workspace
import dagger.BindsInstance
import dagger.Component
import javax.inject.Inject

/** Tests for [PackageCheckerImpl]. */
class PackageCheckerImplTest : PackageCheckerTest() {

  @Inject lateinit var checker: PackageChecker

  override fun setupSubject(workspace: Workspace) {
    DaggerPackageCheckerImplTest_PackageCheckerTestComponent.factory()
        .create(workspace)
        .inject(this)
  }

  override fun subject() = checker

  @Component(modules = [PackageCheckerImplModule::class])
  internal interface PackageCheckerTestComponent {
    fun inject(test: PackageCheckerImplTest)

    @Component.Factory
    interface Factory {
      fun create(@BindsInstance workspace: Workspace): PackageCheckerTestComponent
    }
  }
}
