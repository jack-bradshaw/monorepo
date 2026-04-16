package com.jackbradshaw.oksp.application

import kotlinx.coroutines.runBlocking
import org.junit.Test

abstract class ApplicationTest {

  /** Gets the subject under test. The same instance must be returned each call. */
  abstract fun subject(): Application

  /** Prepares the environment and returns a component to pass to the application. */
  abstract fun setupSubject(): ApplicationComponent

  @Test
  fun onCreate_doesNotFail() = runBlocking {
    val component = setupSubject()
    subject().onCreate(component)
  }

  @Test
  fun onDestroy_onceAfterOnCreate_doesNotFail() = runBlocking {
    val component = setupSubject()
    subject().onCreate(component)
    subject().onDestroy()
  }

  @Test
  fun onDestroy_twiceAfterOnCreate_doesNotFail() = runBlocking {
    val component = setupSubject()
    subject().onCreate(component)
    subject().onDestroy()
    subject().onDestroy()
  }

  @Test
  fun onDestroy_withoutOnCreate_doesNotFail() = runBlocking {
    setupSubject()
    subject().onDestroy()
  }
}
