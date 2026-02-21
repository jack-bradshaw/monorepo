package com.jackbradshaw.backstab.ksp.adapters.tests

import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

/**
 * A shared verification class used to trigger the KSP compilation cycle for symbol-driven tests.
 *
 * This class contains a dummy test method which is enough to satisfy JUnit and the build system,
 * while allowing the configured KSP plugins to perform their assertions at build time.
 *
 * Since the test assertions live in KSP, tests will fail before this class even runs.
 */
@RunWith(JUnit4::class)
class Stub {
  @Test
  fun stub() {
    // No-op. KSP processors perform assertions during compilation.
  }
}
