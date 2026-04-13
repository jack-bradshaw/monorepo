package com.jackbradshaw.backstab.core.model.kotlinpoet

import com.google.common.truth.Truth.assertThat
import com.jackbradshaw.backstab.core.model.Type
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.STAR
import com.squareup.kotlinpoet.WildcardTypeName
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class TypeArgumentTest {

  /**
   * Checks whether a type argument with the following format is converted correctly:
   * ```
   * *
   * ```
   */
  @Test
  fun typeArgument_star_toTypeName() {
    val arg = Type.TypeArgument.Star

    assertThat(arg.toTypeName()).isEqualTo(STAR)
  }

  /**
   * Checks whether a type argument with the following format is converted correctly:
   * ```
   * out Foo
   * ```
   */
  @Test
  fun typeArgument_covariant_simple_toTypeName() {
    val type = Type(packageName = "com.example", nameChain = listOf("Foo"))
    val arg = Type.TypeArgument.Specific(type, Type.TypeArgument.Variance.COVARIANT)

    assertThat(arg.toTypeName())
        .isEqualTo(WildcardTypeName.producerOf(ClassName("com.example", "Foo")))
  }

  /**
   * Checks whether a type argument with the following format is converted correctly:
   * ```
   * out List<String>
   * ```
   */
  @Test
  fun typeArgument_covariant_nested_toTypeName() {
    val nestedType =
        Type(
            packageName = "java.util",
            nameChain = listOf("List"),
            typeArguments =
                listOf(
                    Type.TypeArgument.Specific(
                        Type(packageName = "java.lang", nameChain = listOf("String")),
                        Type.TypeArgument.Variance.INVARIANT)))
    val arg = Type.TypeArgument.Specific(nestedType, Type.TypeArgument.Variance.COVARIANT)

    val expectedBase =
        ClassName("java.util", "List").parameterizedBy(ClassName("java.lang", "String"))
    assertThat(arg.toTypeName()).isEqualTo(WildcardTypeName.producerOf(expectedBase))
  }

  /**
   * Checks whether a type argument with the following format is converted correctly:
   * ```
   * out Outer.Inner.Deep
   * ```
   */
  @Test
  fun typeArgument_covariant_long_toTypeName() {
    val longType = Type(packageName = "com.example", nameChain = listOf("Outer", "Inner", "Deep"))
    val arg = Type.TypeArgument.Specific(longType, Type.TypeArgument.Variance.COVARIANT)

    assertThat(arg.toTypeName())
        .isEqualTo(WildcardTypeName.producerOf(ClassName("com.example", "Outer", "Inner", "Deep")))
  }

  /**
   * Checks whether a type argument with the following format is converted correctly:
   * ```
   * out Foo?
   * ```
   */
  @Test
  fun typeArgument_covariant_nullable_toTypeName() {
    val nullableType =
        Type(packageName = "com.example", nameChain = listOf("Foo"), isNullable = true)
    val arg = Type.TypeArgument.Specific(nullableType, Type.TypeArgument.Variance.COVARIANT)

    assertThat(arg.toTypeName())
        .isEqualTo(
            WildcardTypeName.producerOf(ClassName("com.example", "Foo").copy(nullable = true)))
  }

  /**
   * Checks whether a type argument with the following format is converted correctly:
   * ```
   * in Foo
   * ```
   */
  @Test
  fun typeArgument_contravariant_simple_toTypeName() {
    val type = Type(packageName = "com.example", nameChain = listOf("Foo"))
    val arg = Type.TypeArgument.Specific(type, Type.TypeArgument.Variance.CONTRAVARIANT)

    assertThat(arg.toTypeName())
        .isEqualTo(WildcardTypeName.consumerOf(ClassName("com.example", "Foo")))
  }

  /**
   * Checks whether a type argument with the following format is converted correctly:
   * ```
   * in List<String>
   * ```
   */
  @Test
  fun typeArgument_contravariant_nested_toTypeName() {
    val nestedType =
        Type(
            packageName = "java.util",
            nameChain = listOf("List"),
            typeArguments =
                listOf(
                    Type.TypeArgument.Specific(
                        Type(packageName = "java.lang", nameChain = listOf("String")),
                        Type.TypeArgument.Variance.INVARIANT)))
    val arg = Type.TypeArgument.Specific(nestedType, Type.TypeArgument.Variance.CONTRAVARIANT)

    val expectedBase =
        ClassName("java.util", "List").parameterizedBy(ClassName("java.lang", "String"))
    assertThat(arg.toTypeName()).isEqualTo(WildcardTypeName.consumerOf(expectedBase))
  }

  /**
   * Checks whether a type argument with the following format is converted correctly:
   * ```
   * in Outer.Inner.Deep
   * ```
   */
  @Test
  fun typeArgument_contravariant_long_toTypeName() {
    val longType = Type(packageName = "com.example", nameChain = listOf("Outer", "Inner", "Deep"))
    val arg = Type.TypeArgument.Specific(longType, Type.TypeArgument.Variance.CONTRAVARIANT)

    assertThat(arg.toTypeName())
        .isEqualTo(WildcardTypeName.consumerOf(ClassName("com.example", "Outer", "Inner", "Deep")))
  }

  /**
   * Checks whether a type argument with the following format is converted correctly:
   * ```
   * in Foo?
   * ```
   */
  @Test
  fun typeArgument_contravariant_nullable_toTypeName() {
    val nullableType =
        Type(packageName = "com.example", nameChain = listOf("Foo"), isNullable = true)
    val arg = Type.TypeArgument.Specific(nullableType, Type.TypeArgument.Variance.CONTRAVARIANT)

    assertThat(arg.toTypeName())
        .isEqualTo(
            WildcardTypeName.consumerOf(ClassName("com.example", "Foo").copy(nullable = true)))
  }

  /**
   * Checks whether a type argument with the following format is converted correctly:
   * ```
   * Foo
   * ```
   */
  @Test
  fun typeArgument_invariant_simple_toTypeName() {
    val type = Type(packageName = "com.example", nameChain = listOf("Foo"))
    val arg = Type.TypeArgument.Specific(type, Type.TypeArgument.Variance.INVARIANT)

    assertThat(arg.toTypeName()).isEqualTo(ClassName("com.example", "Foo"))
  }

  /**
   * Checks whether a type argument with the following format is converted correctly:
   * ```
   * List<String>
   * ```
   */
  @Test
  fun typeArgument_invariant_nested_toTypeName() {
    val nestedType =
        Type(
            packageName = "java.util",
            nameChain = listOf("List"),
            typeArguments =
                listOf(
                    Type.TypeArgument.Specific(
                        Type(packageName = "java.lang", nameChain = listOf("String")),
                        Type.TypeArgument.Variance.INVARIANT)))
    val arg = Type.TypeArgument.Specific(nestedType, Type.TypeArgument.Variance.INVARIANT)

    val expectedBase =
        ClassName("java.util", "List").parameterizedBy(ClassName("java.lang", "String"))
    assertThat(arg.toTypeName()).isEqualTo(expectedBase)
  }

  /**
   * Checks whether a type argument with the following format is converted correctly:
   * ```
   * Outer.Inner.Deep
   * ```
   */
  @Test
  fun typeArgument_invariant_long_toTypeName() {
    val longType = Type(packageName = "com.example", nameChain = listOf("Outer", "Inner", "Deep"))
    val arg = Type.TypeArgument.Specific(longType, Type.TypeArgument.Variance.INVARIANT)

    assertThat(arg.toTypeName()).isEqualTo(ClassName("com.example", "Outer", "Inner", "Deep"))
  }

  /**
   * Checks whether a type argument with the following format is converted correctly:
   * ```
   * Foo?
   * ```
   */
  @Test
  fun typeArgument_invariant_nullable_toTypeName() {
    val nullableType =
        Type(packageName = "com.example", nameChain = listOf("Foo"), isNullable = true)
    val arg = Type.TypeArgument.Specific(nullableType, Type.TypeArgument.Variance.INVARIANT)

    assertThat(arg.toTypeName()).isEqualTo(ClassName("com.example", "Foo").copy(nullable = true))
  }
}
