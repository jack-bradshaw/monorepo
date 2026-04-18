package com.jackbradshaw.oksp.application.loaded.apploader

import com.google.common.truth.Truth.assertThat
import com.jackbradshaw.oksp.application.Application
import kotlin.test.assertFailsWith
import org.junit.Test

abstract class ApplicationLoaderTest {

  @Test
  fun notDeclared_fails() {
    setupSubject(applications = emptyList())

    val error = assertFailsWith<IllegalArgumentException> { subject().load() }
    assertThat(error)
        .hasMessageThat()
        .isEqualTo(
            "No Application implementation found. " +
                "Ensure this JAR contains an application declartion in " +
                "META-INF/services/com.jackbradshaw.oksp.application.Application")
  }

  @Test
  fun declaredOnce_loads() {
    setupSubject(applications = listOf(TestApp1::class.java))

    val application = subject().load()

    assertThat(application).isNotNull()
  }

  @Test
  fun declaredRepeatedly_fails() {
    setupSubject(applications = listOf(TestApp1::class.java, TestApp2::class.java))

    val error = assertFailsWith<IllegalArgumentException> { subject().load() }
    assertThat(error)
        .hasMessageThat()
        .isEqualTo(
            "Multiple Application implementations found. " +
                "Ensure this JAR contains exactly one application declartion in " +
                "META-INF/services/com.jackbradshaw.oksp.application.Application")
  }

  /** Gets the subject under test. The same instance must be returned each call. */
  abstract fun subject(): ApplicationLoader

  /** Prepares the environment so that [subject] will observe the specified [applications]. */
  abstract fun setupSubject(applications: List<Class<*>>)
}

class TestApp1 : Application {
  override suspend fun onCreate(component: Application.ContextComponent) {}

  override suspend fun onDestroy() {}
}

class TestApp2 : Application {
  override suspend fun onCreate(component: Application.ContextComponent) {}

  override suspend fun onDestroy() {}
}
