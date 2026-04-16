package com.jackbradshaw.backstab.ksp.adapters

import com.google.common.truth.Truth.assertThat
import com.google.devtools.ksp.KspExperimental
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.symbol.KSPropertyDeclaration
import com.google.devtools.ksp.symbol.KSTypeArgument
import com.jackbradshaw.backstab.core.model.Type
import com.jackbradshaw.kale.ksprunner.JvmSource
import com.jackbradshaw.kale.resolver.rule.ResolverTestRule
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class KsTypeArgumentTest {

  @get:Rule val configuration = ResolverTestRule()

  private val sharedSource =
      JvmSource(
          packageName = "com.foo",
          fileName = "SharedTestPropertyTypes",
          extension = "kt",
          contents =
              """
      package com.foo

      class Foo
      class Box<T>
      class Wrapper<T>
      class InnerType
      class Outer {
        class Inner {
          class Deep
        }
      }

      val contravariantLongProperty: Box<in Outer.Inner.Deep>? = null
      val contravariantNestedProperty: Box<in Wrapper<InnerType>>? = null
      val contravariantNullableProperty: Box<in Foo?>? = null
      val contravariantSimpleProperty: Box<in Foo>? = null

      val covariantLongProperty: Box<out Outer.Inner.Deep>? = null
      val covariantNestedProperty: Box<out Wrapper<InnerType>>? = null
      val covariantNullableProperty: Box<out Foo?>? = null
      val covariantSimpleProperty: Box<out Foo>? = null

      val invariantLongProperty: Box<Outer.Inner.Deep>? = null
      val invariantNestedProperty: Box<Wrapper<InnerType>>? = null
      val invariantNullableProperty: Box<Foo?>? = null
      val invariantSimpleProperty: Box<Foo>? = null
      
      class StarBox<T>
      val starProperty: StarBox<*>? = null
    """
                  .trimIndent())

  @Test
  fun typeArgument_star() {
    configuration.withResolver(setOf(sharedSource)) { resolver ->
      val arg = resolveArgument(resolver, "starProperty")
      assertThat(arg.toTypeArgument()).isEqualTo(Type.TypeArgument.Star)
    }
  }

  @Test
  fun typeArgument_covariant_simple() {
    configuration.withResolver(setOf(sharedSource)) { resolver ->
      val arg = resolveArgument(resolver, "covariantSimpleProperty")
      val expected =
          Type.TypeArgument.Specific(
              Type(packageName = "com.foo", nameChain = listOf("Foo")),
              Type.TypeArgument.Variance.COVARIANT)
      assertThat(arg.toTypeArgument()).isEqualTo(expected)
    }
  }

  @Test
  fun typeArgument_covariant_nested() {
    configuration.withResolver(setOf(sharedSource)) { resolver ->
      val arg = resolveArgument(resolver, "covariantNestedProperty")

      val nestedType =
          Type(
              packageName = "com.foo",
              nameChain = listOf("Wrapper"),
              typeArguments =
                  listOf(
                      Type.TypeArgument.Specific(
                          Type(packageName = "com.foo", nameChain = listOf("InnerType")),
                          Type.TypeArgument.Variance.INVARIANT)))
      val expected = Type.TypeArgument.Specific(nestedType, Type.TypeArgument.Variance.COVARIANT)

      assertThat(arg.toTypeArgument()).isEqualTo(expected)
    }
  }

  @Test
  fun typeArgument_covariant_long() {
    configuration.withResolver(setOf(sharedSource)) { resolver ->
      val arg = resolveArgument(resolver, "covariantLongProperty")

      val expected =
          Type.TypeArgument.Specific(
              Type(packageName = "com.foo", nameChain = listOf("Outer", "Inner", "Deep")),
              Type.TypeArgument.Variance.COVARIANT)
      assertThat(arg.toTypeArgument()).isEqualTo(expected)
    }
  }

  @Test
  fun typeArgument_covariant_nullable() {
    configuration.withResolver(setOf(sharedSource)) { resolver ->
      val arg = resolveArgument(resolver, "covariantNullableProperty")

      val expected =
          Type.TypeArgument.Specific(
              Type(packageName = "com.foo", nameChain = listOf("Foo"), isNullable = true),
              Type.TypeArgument.Variance.COVARIANT)
      assertThat(arg.toTypeArgument()).isEqualTo(expected)
    }
  }

  @Test
  fun typeArgument_contravariant_simple() {
    configuration.withResolver(setOf(sharedSource)) { resolver ->
      val arg = resolveArgument(resolver, "contravariantSimpleProperty")

      val expected =
          Type.TypeArgument.Specific(
              Type(packageName = "com.foo", nameChain = listOf("Foo")),
              Type.TypeArgument.Variance.CONTRAVARIANT)
      assertThat(arg.toTypeArgument()).isEqualTo(expected)
    }
  }

  @Test
  fun typeArgument_contravariant_nested() {
    configuration.withResolver(setOf(sharedSource)) { resolver ->
      val arg = resolveArgument(resolver, "contravariantNestedProperty")

      val nestedType =
          Type(
              packageName = "com.foo",
              nameChain = listOf("Wrapper"),
              typeArguments =
                  listOf(
                      Type.TypeArgument.Specific(
                          Type(packageName = "com.foo", nameChain = listOf("InnerType")),
                          Type.TypeArgument.Variance.INVARIANT)))
      val expected =
          Type.TypeArgument.Specific(nestedType, Type.TypeArgument.Variance.CONTRAVARIANT)

      assertThat(arg.toTypeArgument()).isEqualTo(expected)
    }
  }

  @Test
  fun typeArgument_contravariant_long() {
    configuration.withResolver(setOf(sharedSource)) { resolver ->
      val arg = resolveArgument(resolver, "contravariantLongProperty")

      val expected =
          Type.TypeArgument.Specific(
              Type(packageName = "com.foo", nameChain = listOf("Outer", "Inner", "Deep")),
              Type.TypeArgument.Variance.CONTRAVARIANT)
      assertThat(arg.toTypeArgument()).isEqualTo(expected)
    }
  }

  @Test
  fun typeArgument_contravariant_nullable() {
    configuration.withResolver(setOf(sharedSource)) { resolver ->
      val arg = resolveArgument(resolver, "contravariantNullableProperty")

      val expected =
          Type.TypeArgument.Specific(
              Type(packageName = "com.foo", nameChain = listOf("Foo"), isNullable = true),
              Type.TypeArgument.Variance.CONTRAVARIANT)
      assertThat(arg.toTypeArgument()).isEqualTo(expected)
    }
  }

  @Test
  fun typeArgument_invariant_simple() {
    configuration.withResolver(setOf(sharedSource)) { resolver ->
      val arg = resolveArgument(resolver, "invariantSimpleProperty")

      val expected =
          Type.TypeArgument.Specific(
              Type(packageName = "com.foo", nameChain = listOf("Foo")),
              Type.TypeArgument.Variance.INVARIANT)
      assertThat(arg.toTypeArgument()).isEqualTo(expected)
    }
  }

  @Test
  fun typeArgument_invariant_nested() {
    configuration.withResolver(setOf(sharedSource)) { resolver ->
      val arg = resolveArgument(resolver, "invariantNestedProperty")

      val nestedType =
          Type(
              packageName = "com.foo",
              nameChain = listOf("Wrapper"),
              typeArguments =
                  listOf(
                      Type.TypeArgument.Specific(
                          Type(packageName = "com.foo", nameChain = listOf("InnerType")),
                          Type.TypeArgument.Variance.INVARIANT)))
      val expected = Type.TypeArgument.Specific(nestedType, Type.TypeArgument.Variance.INVARIANT)

      assertThat(arg.toTypeArgument()).isEqualTo(expected)
    }
  }

  @Test
  fun typeArgument_invariant_long() {
    configuration.withResolver(setOf(sharedSource)) { resolver ->
      val arg = resolveArgument(resolver, "invariantLongProperty")

      val expected =
          Type.TypeArgument.Specific(
              Type(packageName = "com.foo", nameChain = listOf("Outer", "Inner", "Deep")),
              Type.TypeArgument.Variance.INVARIANT)
      assertThat(arg.toTypeArgument()).isEqualTo(expected)
    }
  }

  @Test
  fun typeArgument_invariant_nullable() {
    configuration.withResolver(setOf(sharedSource)) { resolver ->
      val arg = resolveArgument(resolver, "invariantNullableProperty")

      val expected =
          Type.TypeArgument.Specific(
              Type(packageName = "com.foo", nameChain = listOf("Foo"), isNullable = true),
              Type.TypeArgument.Variance.INVARIANT)
      assertThat(arg.toTypeArgument()).isEqualTo(expected)
    }
  }

  @OptIn(KspExperimental::class)
  private fun resolveArgument(resolver: Resolver, propertyName: String): KSTypeArgument {
    val property =
        checkNotNull(
            resolver
                .getDeclarationsFromPackage("com.foo")
                .filterIsInstance<KSPropertyDeclaration>()
                .firstOrNull { it.simpleName.asString() == propertyName }) {
              "Could not resolve $propertyName"
            }

    return property.type.resolve().arguments.first()
  }
}
