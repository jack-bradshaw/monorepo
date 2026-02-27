package com.jackbradshaw.backstab.ksp.adapters.tests.kstype

import com.google.devtools.ksp.processing.Resolver
import com.google.common.truth.Truth.assertThat
import com.google.devtools.ksp.KspExperimental
import com.jackbradshaw.oksp.application.Application
import com.google.devtools.ksp.symbol.KSPropertyDeclaration
import com.google.devtools.ksp.symbol.KSType
import com.jackbradshaw.backstab.core.model.Type
import com.jackbradshaw.backstab.ksp.adapters.toType
import com.jackbradshaw.backstab.ksp.testing.SymbolProcessorTest

class KsTypeExtensionsTest : SymbolProcessorTest() {

  override fun supplyCases(): Map<String, (Resolver) -> Unit> =
      mapOf(
          "toType_simpleLevel" to ::test_toType_simpleLevel,
          "toType_nestedLevel" to ::test_toType_nestedLevel,
          "toType_nullable" to ::test_toType_nullable,
          "toType_typeArguments" to ::test_toType_typeArguments,
          "toType_starProjection" to ::test_toType_starProjection,
          "toType_covariant" to ::test_toType_covariant,
          "toType_contravariant" to ::test_toType_contravariant,
          "toType_nestedTypeArguments" to ::test_toType_nestedTypeArguments,
          "toType_multipleTypeArguments" to ::test_toType_multipleTypeArguments,
          "toType_complex" to ::test_toType_complex)

  private fun test_toType_simpleLevel(resolver: Resolver) {
    val converted = resolveProperty(resolver, "testPropertySimple").toType()

    assertThat(converted).isEqualTo(Type(packageName = "com.foo", nameChain = listOf("Foo")))
  }

  private fun test_toType_nestedLevel(resolver: Resolver) {
    val converted = resolveProperty(resolver, "testPropertyNested").toType()

    assertThat(converted)
        .isEqualTo(Type(packageName = "com.foo", nameChain = listOf("Outer", "Inner")))
  }

  private fun test_toType_nullable(resolver: Resolver) {
    val converted = resolveProperty(resolver, "testPropertyNullable").toType()

    assertThat(converted)
        .isEqualTo(
            Type(packageName = "com.foo", nameChain = listOf("NullableFoo"), isNullable = true))
  }

  private fun test_toType_typeArguments(resolver: Resolver) {
    val converted = resolveProperty(resolver, "testPropertyTypeArguments").toType()

    val expected =
        Type(
            packageName = "com.foo",
            nameChain = listOf("TypeArgBox"),
            typeArguments =
                listOf(
                    Type.TypeArgument.Specific(
                        type = Type(packageName = "com.foo", nameChain = listOf("TypeArgFoo")),
                        variance = Type.TypeArgument.Variance.INVARIANT)))
    assertThat(converted).isEqualTo(expected)
  }

  @OptIn(KspExperimental::class)
  private fun test_toType_starProjection(resolver: Resolver) {
    val converted = resolveProperty(resolver, "testPropertyStarProjection").toType()

    assertThat(converted)
        .isEqualTo(
            Type(
                packageName = "com.foo",
                nameChain = listOf("StarBox"),
                typeArguments = listOf(Type.TypeArgument.Star)))
  }

  private fun test_toType_covariant(resolver: Resolver) {
    val converted = resolveProperty(resolver, "testPropertyCovariant").toType()

    val expected =
        Type(
            packageName = "com.foo",
            nameChain = listOf("CovarCovariant"),
            typeArguments =
                listOf(
                    Type.TypeArgument.Specific(
                        Type(packageName = "com.foo", nameChain = listOf("CovarBound")),
                        Type.TypeArgument.Variance.INVARIANT)))
    assertThat(converted).isEqualTo(expected)
  }

  private fun test_toType_contravariant(resolver: Resolver) {
    val converted = resolveProperty(resolver, "testPropertyContravariant").toType()

    val expected =
        Type(
            packageName = "com.foo",
            nameChain = listOf("ContraContravariant"),
            typeArguments =
                listOf(
                    Type.TypeArgument.Specific(
                        Type(packageName = "com.foo", nameChain = listOf("ContraBound")),
                        Type.TypeArgument.Variance.INVARIANT)))
    assertThat(converted).isEqualTo(expected)
  }

  private fun test_toType_nestedTypeArguments(resolver: Resolver) {
    val converted = resolveProperty(resolver, "testPropertyNestedTypeArgerics").toType()

    val expected =
        Type(
            packageName = "com.foo",
            nameChain = listOf("NestedTypeArgWrapper"),
            typeArguments =
                listOf(
                    Type.TypeArgument.Specific(
                        Type(
                            packageName = "com.foo",
                            nameChain = listOf("NestedTypeArgInner"),
                            typeArguments =
                                listOf(
                                    Type.TypeArgument.Specific(
                                        Type(
                                            packageName = "com.foo",
                                            nameChain = listOf("NestedTypeArgBound")),
                                        Type.TypeArgument.Variance.INVARIANT))),
                        Type.TypeArgument.Variance.INVARIANT)))
    assertThat(converted).isEqualTo(expected)
  }

  private fun test_toType_multipleTypeArguments(resolver: Resolver) {
    val converted = resolveProperty(resolver, "testPropertyMultipleTypeArguments").toType()

    val expected =
        Type(
            packageName = "com.foo",
            nameChain = listOf("MultiTypeArguments"),
            typeArguments =
                listOf(
                    Type.TypeArgument.Specific(
                        Type(packageName = "com.foo", nameChain = listOf("MultiKey")),
                        Type.TypeArgument.Variance.INVARIANT),
                    Type.TypeArgument.Specific(
                        Type(packageName = "com.foo", nameChain = listOf("MultiValue")),
                        Type.TypeArgument.Variance.INVARIANT),
                    Type.TypeArgument.Specific(
                        Type(packageName = "com.foo", nameChain = listOf("MultiTrigger")),
                        Type.TypeArgument.Variance.INVARIANT)))
    assertThat(converted).isEqualTo(expected)
  }

  private fun test_toType_complex(resolver: Resolver) {
    val converted = resolveProperty(resolver, "testPropertyComplex").toType()

    val expected =
        Type(
            packageName = "com.foo",
            nameChain = listOf("ComplexOuter", "ComplexInner", "ComplexDeeplyNested"),
            isNullable = true,
            typeArguments =
                listOf(
                    Type.TypeArgument.Specific(
                        Type(
                            packageName = "com.foo",
                            nameChain = listOf("ComplexBound"),
                            isNullable = true),
                        Type.TypeArgument.Variance.INVARIANT)))
    assertThat(converted).isEqualTo(expected)
  }

  @OptIn(KspExperimental::class)
  private fun resolveProperty(resolver: Resolver, name: String): KSType {
    val property =
        checkNotNull(
            resolver
                .getDeclarationsFromPackage("com.foo")
                .filterIsInstance<KSPropertyDeclaration>()
                .firstOrNull { it.simpleName.asString() == name }) {
              "Could not find property $name"
            }

    return property.type.resolve()
  }

  class TestApplication : Application by KsTypeExtensionsTest()
}
