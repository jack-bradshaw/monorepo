package com.jackbradshaw.oksp.application.loader

import com.google.common.truth.Truth.assertThat
import org.junit.Test
import kotlin.test.assertFailsWith
import com.jackbradshaw.oksp.application.Application

abstract class ApplicationLoaderTest {

  @Test
  fun notDeclared_fails() {
    setupSubject(DeclarationState.NOT_DECLARED)
    
    val error = assertFailsWith<IllegalArgumentException> {
      subject().load()
    }
    assertThat(error).hasMessageThat().isEqualTo("No Application implementation found. Please ensure your artefact exports a service implementation in META-INF/services/com.jackbradshaw.oksp.application.Application")
  }

  @Test
  fun declaredOnce_loads() {
    setupSubject(DeclarationState.DECLARED_ONCE)
    
    val application = subject().load()
    
    assertThat(application).isNotNull()
  }

  @Test
  fun declaredRepeatedly_fails() {
    setupSubject(DeclarationState.DECLARED_REPEATEDLY)
    
    val error = assertFailsWith<IllegalArgumentException> {
      subject().load()
    }
    assertThat(error).hasMessageThat().isEqualTo("Multiple Application implementations found. Only one application per artefact is supported.")
  }

  /** Gets the subject under test. The same instance must be returned each call. */
  abstract fun subject(): ApplicationLoader

  /** Prepares the environment so that [subject] will observe the specified [DeclarationState]. */
  abstract fun setupSubject(state: DeclarationState)

  enum class DeclarationState {
    NOT_DECLARED,
    DECLARED_ONCE,
    DECLARED_REPEATEDLY
  }
}
