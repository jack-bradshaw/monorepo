package com.jackbradshaw.publicity.conformance.packagechecker

import java.io.File

/** Checks whether individual directories are conformant Bazel packages. */
interface PackageChecker {
  /** Checks [packageDir] for conformance and returns the result. */
  fun validate(packageDir: File): Result

  /** Result of a validation check. */
  sealed class Result {
    /** The package is fully conformant. */
    object Success : Result()

    /** The package is non-conformant (details in [message]). */
    data class Failure(val message: String) : Result()
  }
}
