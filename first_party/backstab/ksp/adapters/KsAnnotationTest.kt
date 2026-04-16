package com.jackbradshaw.backstab.ksp.adapters

import com.google.common.truth.Truth.assertThat
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.symbol.KSAnnotation
import com.jackbradshaw.backstab.core.model.BackstabTarget
import com.jackbradshaw.kale.ksprunner.JvmSource
import com.jackbradshaw.kale.resolver.rule.ResolverTestRule
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class KsAnnotationTest {

  @get:Rule val configuration = ResolverTestRule()

  @Test
  fun toQualifier_customQualifier() {
    val source =
        JvmSource(
            packageName = "com.foo",
            fileName = "toQualifier_customQualifier_input",
            extension = "kt",
            contents =
                """
        package com.foo
        import javax.inject.Qualifier
        @Qualifier annotation class MyQualifier
        @MyQualifier class AnnotatedClassCustom
      """
                    .trimIndent())

    configuration.withResolver(setOf(source)) { resolver ->
      val annotation = resolveClass(resolver, "com.foo.MyQualifier")
      val converted = annotation.toQualifier()

      val expected =
          BackstabTarget.Qualifier.Custom(
              packageName = "com.foo", nameChain = listOf("MyQualifier"))
      assertThat(converted).isEqualTo(expected)
    }
  }

  @Test
  fun toQualifier_namedAnnotation() {
    val source =
        JvmSource(
            packageName = "com.foo",
            fileName = "toQualifier_namedAnnotation_input",
            extension = "kt",
            contents =
                """
          package com.foo
          import javax.inject.Named
          @Named("foo") class AnnotatedClass
       """
                    .trimIndent())

    configuration.withResolver(setOf(source)) { resolver ->
      val annotation = resolveClass(resolver, "javax.inject.Named")
      val converted = annotation.toQualifier()

      assertThat(converted).isEqualTo(BackstabTarget.Qualifier.Named("foo"))
    }
  }

  @Test
  fun toQualifier_notQualifier() {
    val source =
        JvmSource(
            packageName = "com.foo",
            fileName = "toQualifier_notQualifier_input",
            extension = "kt",
            contents =
                """
          package com.foo
          annotation class NotAQualifier
          @NotAQualifier class AnnotatedClassNotQualifier
       """
                    .trimIndent())

    configuration.withResolver(setOf(source)) { resolver ->
      val annotation = resolveClass(resolver, "com.foo.NotAQualifier")

      try {
        annotation.toQualifier()
        throw AssertionError("Expected IllegalArgumentException")
      } catch (e: IllegalArgumentException) {
        // Expected
      }
    }
  }

  private fun resolveClass(resolver: Resolver, annotationName: String): KSAnnotation {
    val annotationType =
        checkNotNull(resolver.getKSNameFromString(annotationName)) {
          "Could not find annotation $annotationName"
        }

    val annotatedSymbol =
        resolver.getSymbolsWithAnnotation(annotationName).firstOrNull()
            ?: throw AssertionError("Could not find any symbols annotated with $annotationName")

    return checkNotNull(annotatedSymbol.annotations.firstOrNull()) {
      "No annotations found on symbol $annotatedSymbol"
    }
  }
}
