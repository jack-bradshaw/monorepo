package com.jackbradshaw.publicity.conformance.packagechecker

import com.jackbradshaw.publicity.conformance.ConformanceScope
import com.jackbradshaw.publicity.conformance.FirstPartyRoot
import com.jackbradshaw.publicity.conformance.WorkspaceRoot
import dagger.BindsInstance
import dagger.Component
import java.io.File

/** Tests for [PackageCheckerImpl]. */
class PackageCheckerImplTest : PackageCheckerTest() {
  private lateinit var checker: PackageChecker

  override fun setupSubject(workspaceRoot: File, firstPartyRoot: String) {
    checker =
        DaggerPackageCheckerImplTest_PackageCheckerTestComponent.factory()
            .create(workspaceRoot, firstPartyRoot)
            .checker()
  }

  override fun subject() = checker

  @ConformanceScope
  @Component(modules = [PackageCheckerImplModule::class])
  internal interface PackageCheckerTestComponent {
    fun checker(): PackageChecker

    @Component.Factory
    interface Factory {
      fun create(
          @BindsInstance @WorkspaceRoot workspaceRoot: File,
          @BindsInstance @FirstPartyRoot firstPartyRoot: String
      ): PackageCheckerTestComponent
    }
  }
}
