package com.jackbradshaw.publicity.conformance.packagechecker

import dagger.Binds
import dagger.Module

@Module
interface PackageCheckerImplModule {
  @Binds fun bindPackageChecker(impl: PackageCheckerImpl): PackageChecker
}
