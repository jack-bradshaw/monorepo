package com.jackbradshaw.oksp.application

import com.jackbradshaw.oksp.testing.application.chassis.ApplicationChassis
import kotlinx.coroutines.runBlocking
import org.junit.Test

abstract class ApplicationTest {

  /** Gets the chassis to use for running the test. */
  abstract fun chassis(): ApplicationChassis

  /** Gets the subject under test. The same instance must be returned each call. */
  abstract fun subject(): Application

  @Test fun run_startsAndCompletesCleanly() = runBlocking { chassis().run(subject()) }
}
