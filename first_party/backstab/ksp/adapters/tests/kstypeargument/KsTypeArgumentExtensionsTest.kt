package com.jackbradshaw.backstab.ksp.adapters.tests.kstypeargument

import com.google.common.truth.Truth.assertThat
import com.google.devtools.ksp.KspExperimental
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.processing.SymbolProcessorProvider
import com.google.devtools.ksp.symbol.KSPropertyDeclaration
import com.google.devtools.ksp.symbol.KSTypeArgument
import com.jackbradshaw.backstab.core.model.Type
import com.jackbradshaw.backstab.ksp.adapters.toTypeArgument
import com.jackbradshaw.backstab.ksp.testing.SymbolProcessorTest

class KsTypeArgumentExtensionsTest(env: SymbolProcessorEnvironment) : SymbolProcessorTest(env) {

  override fun supplyCases(): Map<String, (Resolver) -> Unit> =
      mapOf(
          "typeArgument_star" to ::test_typeArgument_star,
          "typeArgument_covariant_simple" to ::test_typeArgument_covariant_simple,
          "typeArgument_covariant_nested" to ::test_typeArgument_covariant_nested,
          "typeArgument_covariant_long" to ::test_typeArgument_covariant_long,
          "typeArgument_covariant_nullable" to ::test_typeArgument_covariant_nullable,
          "typeArgument_contravariant_simple" to ::test_typeArgument_contravariant_simple,
          "typeArgument_contravariant_nested" to ::test_typeArgument_contravariant_nested,
          "typeArgument_contravariant_long" to ::test_typeArgument_contravariant_long,
          "typeArgument_contravariant_nullable" to ::test_typeArgument_contravariant_nullable,
          "typeArgument_invariant_simple" to ::test_typeArgument_invariant_simple,
          "typeArgument_invariant_nested" to ::test_typeArgument_invariant_nested,
          "typeArgument_invariant_long" to ::test_typeArgument_invariant_long,
          "typeArgument_invariant_nullable" to ::test_typeArgument_invariant_nullable)

  private fun test_typeArgument_star(resolver: Resolver) {
    val arg = resolveArgument(resolver, "starProperty")

    assertThat(arg.toTypeArgument()).isEqualTo(Type.TypeArgument.Star)
  }

  private fun test_typeArgument_covariant_simple(resolver: Resolver) {
    val arg = resolveArgument(resolver, "covariantSimpleProperty")

    val expected =
        Type.TypeArgument.Specific(
            Type(packageName = "com.foo", nameChain = listOf("Foo")),
            Type.TypeArgument.Variance.COVARIANT)
    assertThat(arg.toTypeArgument()).isEqualTo(expected)
  }

  private fun test_typeArgument_covariant_nested(resolver: Resolver) {
    val arg = resolveArgument(resolver, "covariantNestedProperty")

    val nestedType =
        Type(
            packageName = "java.util",
            nameChain = listOf("List"),
            typeArguments =
                listOf(
                    Type.TypeArgument.Specific(
                        Type(packageName = "java.lang", nameChain = listOf("String")),
                        Type.TypeArgument.Variance.INVARIANT)))
    val expected = Type.TypeArgument.Specific(nestedType, Type.TypeArgument.Variance.COVARIANT)

    assertThat(arg.toTypeArgument()).isEqualTo(expected)
  }

  private fun test_typeArgument_covariant_long(resolver: Resolver) {
    val arg = resolveArgument(resolver, "covariantLongProperty")

    val expected =
        Type.TypeArgument.Specific(
            Type(packageName = "com.foo", nameChain = listOf("Outer", "Inner", "Deep")),
            Type.TypeArgument.Variance.COVARIANT)
    assertThat(arg.toTypeArgument()).isEqualTo(expected)
  }

  private fun test_typeArgument_covariant_nullable(resolver: Resolver) {
    val arg = resolveArgument(resolver, "covariantNullableProperty")

    val expected =
        Type.TypeArgument.Specific(
            Type(packageName = "com.foo", nameChain = listOf("Foo"), isNullable = true),
            Type.TypeArgument.Variance.COVARIANT)
    assertThat(arg.toTypeArgument()).isEqualTo(expected)
  }

  private fun test_typeArgument_contravariant_simple(resolver: Resolver) {
    val arg = resolveArgument(resolver, "contravariantSimpleProperty")

    val expected =
        Type.TypeArgument.Specific(
            Type(packageName = "com.foo", nameChain = listOf("Foo")),
            Type.TypeArgument.Variance.CONTRAVARIANT)
    assertThat(arg.toTypeArgument()).isEqualTo(expected)
  }

  private fun test_typeArgument_contravariant_nested(resolver: Resolver) {
    val arg = resolveArgument(resolver, "contravariantNestedProperty")

    val nestedType =
        Type(
            packageName = "java.util",
            nameChain = listOf("List"),
            typeArguments =
                listOf(
                    Type.TypeArgument.Specific(
                        Type(packageName = "java.lang", nameChain = listOf("String")),
                        Type.TypeArgument.Variance.INVARIANT)))
    val expected = Type.TypeArgument.Specific(nestedType, Type.TypeArgument.Variance.CONTRAVARIANT)

    assertThat(arg.toTypeArgument()).isEqualTo(expected)
  }

  private fun test_typeArgument_contravariant_long(resolver: Resolver) {
    val arg = resolveArgument(resolver, "contravariantLongProperty")

    val expected =
        Type.TypeArgument.Specific(
            Type(packageName = "com.foo", nameChain = listOf("Outer", "Inner", "Deep")),
            Type.TypeArgument.Variance.CONTRAVARIANT)
    assertThat(arg.toTypeArgument()).isEqualTo(expected)
  }

  private fun test_typeArgument_contravariant_nullable(resolver: Resolver) {
    val arg = resolveArgument(resolver, "contravariantNullableProperty")

    val expected =
        Type.TypeArgument.Specific(
            Type(packageName = "com.foo", nameChain = listOf("Foo"), isNullable = true),
            Type.TypeArgument.Variance.CONTRAVARIANT)
    assertThat(arg.toTypeArgument()).isEqualTo(expected)
  }

  private fun test_typeArgument_invariant_simple(resolver: Resolver) {
    val arg = resolveArgument(resolver, "invariantSimpleProperty")

    val expected =
        Type.TypeArgument.Specific(
            Type(packageName = "com.foo", nameChain = listOf("Foo")),
            Type.TypeArgument.Variance.INVARIANT)
    assertThat(arg.toTypeArgument()).isEqualTo(expected)
  }

  private fun test_typeArgument_invariant_nested(resolver: Resolver) {
    val arg = resolveArgument(resolver, "invariantNestedProperty")

    val nestedType =
        Type(
            packageName = "java.util",
            nameChain = listOf("List"),
            typeArguments =
                listOf(
                    Type.TypeArgument.Specific(
                        Type(packageName = "java.lang", nameChain = listOf("String")),
                        Type.TypeArgument.Variance.INVARIANT)))
    val expected = Type.TypeArgument.Specific(nestedType, Type.TypeArgument.Variance.INVARIANT)

    assertThat(arg.toTypeArgument()).isEqualTo(expected)
  }

  private fun test_typeArgument_invariant_long(resolver: Resolver) {
    val arg = resolveArgument(resolver, "invariantLongProperty")

    val expected =
        Type.TypeArgument.Specific(
            Type(packageName = "com.foo", nameChain = listOf("Outer", "Inner", "Deep")),
            Type.TypeArgument.Variance.INVARIANT)
    assertThat(arg.toTypeArgument()).isEqualTo(expected)
  }

  private fun test_typeArgument_invariant_nullable(resolver: Resolver) {
    val arg = resolveArgument(resolver, "invariantNullableProperty")

    val expected =
        Type.TypeArgument.Specific(
            Type(packageName = "com.foo", nameChain = listOf("Foo"), isNullable = true),
            Type.TypeArgument.Variance.INVARIANT)
    assertThat(arg.toTypeArgument()).isEqualTo(expected)
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

  class Provider : SymbolProcessorProvider {
    override fun create(environment: SymbolProcessorEnvironment): SymbolProcessor {
      return KsTypeArgumentExtensionsTest(environment)
    }
  }
}
