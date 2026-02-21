package com.jackbradshaw.backstab.ksp.adapters.tests.ksannotation

import com.google.common.truth.Truth.assertThat
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.processing.SymbolProcessorProvider
import com.google.devtools.ksp.symbol.KSAnnotation
import com.jackbradshaw.backstab.core.model.BackstabTarget
import com.jackbradshaw.backstab.ksp.adapters.toQualifier
import com.jackbradshaw.backstab.ksp.testing.SymbolProcessorTest

class KsAnnotationExtensionsTest(env: SymbolProcessorEnvironment) : SymbolProcessorTest(env) {

  override fun supplyCases(): Map<String, (Resolver) -> Unit> =
      mapOf(
          "toQualifier_customQualifier" to ::test_toQualifier_customQualifier,
          "toQualifier_namedAnnotation" to ::test_toQualifier_namedAnnotation,
          "toQualifier_notQualifier" to ::test_toQualifier_notQualifier)

  private fun test_toQualifier_customQualifier(resolver: Resolver) {
    val annotation = resolveClass(resolver, "com.foo.AnnotatedClassCustom")

    val converted = annotation.toQualifier()

    val expected =
        BackstabTarget.Qualifier.Custom(packageName = "com.foo", nameChain = listOf("MyQualifier"))
    assertThat(converted).isEqualTo(expected)
  }

  private fun test_toQualifier_namedAnnotation(resolver: Resolver) {
    val annotation = resolveClass(resolver, "com.foo.AnnotatedClass")

    val converted = annotation.toQualifier()

    assertThat(converted).isEqualTo(BackstabTarget.Qualifier.Named("foo"))
  }

  private fun test_toQualifier_notQualifier(resolver: Resolver) {
    val annotation = resolveClass(resolver, "com.foo.AnnotatedClassNotQualifier")

    try {
      annotation.toQualifier()
      throw AssertionError("Expected IllegalArgumentException")
    } catch (e: IllegalArgumentException) {
      // Expected
    }
  }

  private fun resolveClass(resolver: Resolver, name: String): KSAnnotation {
    val targetName = checkNotNull(resolver.getKSNameFromString(name)) { "Could not find $name" }
    val targetDeclaration =
        checkNotNull(resolver.getClassDeclarationByName(targetName)) { "Could not resolve $name" }

    return checkNotNull(targetDeclaration.annotations.firstOrNull()) {
      "No annotations found on $name"
    }
  }

  class Provider : SymbolProcessorProvider {
    override fun create(environment: SymbolProcessorEnvironment): SymbolProcessor {
      return KsAnnotationExtensionsTest(environment)
    }
  }
}
