package com.jackbradshaw.backstab.core.model.kotlinpoet

import com.google.common.truth.Truth.assertThat
import com.jackbradshaw.backstab.core.model.Type
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.ParameterizedTypeName
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.STAR
import com.squareup.kotlinpoet.WildcardTypeName
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class TypeTest {

  /**
   * Checks whether a type with the following format is converted correctly:
   * ```
   * Foo
   * ```
   */
  @Test
  fun type_shortNameChain_toTypeName() {
    val type = Type(packageName = "com.example", nameChain = listOf("Foo"))
    val typeName = type.toTypeName()

    assertThat(typeName).isEqualTo(ClassName("com.example", "Foo"))
    assertThat(typeName.isNullable).isFalse()
  }

  /**
   * Checks whether a type with the following format is converted correctly:
   * ```
   * Outer.Inner
   * ```
   */
  @Test
  fun type_longNameChain_toTypeName() {
    val type = Type(packageName = "com.example", nameChain = listOf("Outer", "Inner"))
    assertThat(type.toTypeName()).isEqualTo(ClassName("com.example", "Outer", "Inner"))
  }

  /**
   * Checks whether a type with the following format is converted correctly:
   * ```
   * Level1.Level2.Level3.Level4.Level5.Level6.Level7.Level8.Level9.Level10
   * ```
   */
  @Test
  fun type_veryLongNameChain_toTypeName() {
    val nameChain =
        listOf(
            "Level1",
            "Level2",
            "Level3",
            "Level4",
            "Level5",
            "Level6",
            "Level7",
            "Level8",
            "Level9",
            "Level10")
    val type = Type(packageName = "com.example", nameChain = nameChain)
    assertThat(type.toTypeName())
        .isEqualTo(
            ClassName(
                "com.example",
                "Level1",
                "Level2",
                "Level3",
                "Level4",
                "Level5",
                "Level6",
                "Level7",
                "Level8",
                "Level9",
                "Level10"))
  }

  /**
   * Checks whether a type with the following format is converted correctly:
   * ```
   * Foo?
   * ```
   */
  @Test
  fun type_nullable_toTypeName() {
    val type = Type(packageName = "com.example", nameChain = listOf("Foo"), isNullable = true)
    val typeName = type.toTypeName()

    assertThat(typeName.isNullable).isTrue()
  }

  /**
   * Checks whether a type with the following format is converted correctly:
   * ```
   * List<String>
   * ```
   */
  @Test
  fun type_typeArguments_invariant_toTypeName() {
    val generic =
        Type.TypeArgument.Specific(
            Type(packageName = "java.lang", nameChain = listOf("String")),
            Type.TypeArgument.Variance.INVARIANT)
    val type =
        Type(packageName = "java.util", nameChain = listOf("List"), typeArguments = listOf(generic))
    val typeName = type.toTypeName()

    val expected = ClassName("java.util", "List").parameterizedBy(ClassName("java.lang", "String"))
    assertThat(typeName).isEqualTo(expected)
  }

  /**
   * Checks whether a type with the following format is converted correctly:
   * ```
   * List<out String>
   * ```
   */
  @Test
  fun type_typeArguments_covariant_toTypeName() {
    val generic =
        Type.TypeArgument.Specific(
            Type(packageName = "java.lang", nameChain = listOf("String")),
            Type.TypeArgument.Variance.COVARIANT)
    val type =
        Type(packageName = "java.util", nameChain = listOf("List"), typeArguments = listOf(generic))
    val typeName = type.toTypeName()

    val expected =
        ClassName("java.util", "List")
            .parameterizedBy(WildcardTypeName.producerOf(ClassName("java.lang", "String")))
    assertThat(typeName).isEqualTo(expected)
  }

  /**
   * Checks whether a type with the following format is converted correctly:
   * ```
   * List<in String>
   * ```
   */
  @Test
  fun type_typeArguments_contravariant_toTypeName() {
    val generic =
        Type.TypeArgument.Specific(
            Type(packageName = "java.lang", nameChain = listOf("String")),
            Type.TypeArgument.Variance.CONTRAVARIANT)
    val type =
        Type(packageName = "java.util", nameChain = listOf("List"), typeArguments = listOf(generic))
    val typeName = type.toTypeName()

    val expected =
        ClassName("java.util", "List")
            .parameterizedBy(WildcardTypeName.consumerOf(ClassName("java.lang", "String")))
    assertThat(typeName).isEqualTo(expected)
  }

  /**
   * Checks whether a type with the following format is converted correctly:
   * ```
   * List<*>
   * ```
   */
  @Test
  fun type_typeArguments_star_toTypeName() {
    val type =
        Type(
            packageName = "java.util",
            nameChain = listOf("List"),
            typeArguments = listOf(Type.TypeArgument.Star))
    val typeName = type.toTypeName()

    val expected = ClassName("java.util", "List").parameterizedBy(STAR)
    assertThat(typeName).isEqualTo(expected)
  }

  /**
   * Checks whether a type with the following format is converted correctly:
   * ```
   * Container<Invariant, out Covariant, in Contravariant, *>
   * ```
   */
  @Test
  fun type_typeArguments_multiple_toTypeName() {
    val invariantArg =
        Type.TypeArgument.Specific(
            Type(packageName = "com.example", nameChain = listOf("Invariant")),
            Type.TypeArgument.Variance.INVARIANT)
    val covariantArg =
        Type.TypeArgument.Specific(
            Type(packageName = "com.example", nameChain = listOf("Covariant")),
            Type.TypeArgument.Variance.COVARIANT)
    val contravariantArg =
        Type.TypeArgument.Specific(
            Type(packageName = "com.example", nameChain = listOf("Contravariant")),
            Type.TypeArgument.Variance.CONTRAVARIANT)
    val starArg = Type.TypeArgument.Star

    val type =
        Type(
            packageName = "com.example",
            nameChain = listOf("Container"),
            typeArguments = listOf(invariantArg, covariantArg, contravariantArg, starArg))

    val typeName = type.toTypeName() as ParameterizedTypeName
    assertThat(typeName.rawType).isEqualTo(ClassName("com.example", "Container"))
    assertThat(typeName.typeArguments)
        .containsExactly(
            ClassName("com.example", "Invariant"),
            WildcardTypeName.producerOf(ClassName("com.example", "Covariant")),
            WildcardTypeName.consumerOf(ClassName("com.example", "Contravariant")),
            STAR)
        .inOrder()
  }

  /**
   * Checks whether a type with the following format is converted correctly:
   * ```
   * Wrapper<List<String>>
   * ```
   */
  @Test
  fun type_typeArguments_nested_toTypeName() {
    val nestedInvariant =
        Type.TypeArgument.Specific(
            Type(
                packageName = "java.util",
                nameChain = listOf("List"),
                typeArguments =
                    listOf(
                        Type.TypeArgument.Specific(
                            Type(packageName = "java.lang", nameChain = listOf("String")),
                            Type.TypeArgument.Variance.INVARIANT))),
            Type.TypeArgument.Variance.INVARIANT)
    val type =
        Type(
            packageName = "com.example",
            nameChain = listOf("Wrapper"),
            typeArguments = listOf(nestedInvariant))
    val typeName = type.toTypeName()

    val expected =
        ClassName("com.example", "Wrapper")
            .parameterizedBy(
                ClassName("java.util", "List").parameterizedBy(ClassName("java.lang", "String")))
    assertThat(typeName).isEqualTo(expected)
  }

  /**
   * Checks whether a type with the following format is converted correctly:
   * ```
   * Deep.Nested.Type<out Map<String, *>>?
   * ```
   */
  @Test
  fun type_complex_toTypeName() {
    val genericArg =
        Type.TypeArgument.Specific(
            Type(
                packageName = "java.util",
                nameChain = listOf("Map"),
                typeArguments =
                    listOf(
                        Type.TypeArgument.Specific(
                            Type(packageName = "java.lang", nameChain = listOf("String")),
                            Type.TypeArgument.Variance.INVARIANT),
                        Type.TypeArgument.Star),
                isNullable = true),
            Type.TypeArgument.Variance.COVARIANT)

    val type =
        Type(
            packageName = "com.example",
            nameChain = listOf("Deep", "Nested", "Type"),
            typeArguments = listOf(genericArg),
            isNullable = true)
    val typeName = type.toTypeName()

    val expectedGeneric =
        ClassName("java.util", "Map")
            .parameterizedBy(ClassName("java.lang", "String"), STAR)
            .copy(nullable = true)
    val expected =
        ClassName("com.example", "Deep", "Nested", "Type")
            .parameterizedBy(WildcardTypeName.producerOf(expectedGeneric))
            .copy(nullable = true)

    assertThat(typeName).isEqualTo(expected)
  }
}
