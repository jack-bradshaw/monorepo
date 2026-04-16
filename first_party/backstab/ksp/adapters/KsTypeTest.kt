package com.jackbradshaw.backstab.ksp.adapters

import com.google.common.truth.Truth.assertThat
import com.google.devtools.ksp.KspExperimental
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.symbol.KSPropertyDeclaration
import com.google.devtools.ksp.symbol.KSType
import com.jackbradshaw.backstab.core.model.Type
import com.jackbradshaw.kale.ksprunner.JvmSource
import com.jackbradshaw.kale.resolver.rule.ResolverTestRule
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class KsTypeTest {

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
      val testPropertySimple: Foo = TODO()

      class Outer {
        class Inner
      }
      val testPropertyNested: Outer.Inner = TODO()

      class NullableFoo
      val testPropertyNullable: NullableFoo? = TODO()

      class TypeArgBox<T>
      class TypeArgFoo
      val testPropertyTypeArguments: TypeArgBox<TypeArgFoo> = TODO()

      class StarBox<T>
      val starBox: StarBox<*> = TODO()
      val testPropertyStarProjection: StarBox<*> = TODO()

      class CovarCovariant<out T>
      class CovarBound
      class CovarClass {
        lateinit var covariantField: CovarCovariant<CovarBound>
      }
      val testPropertyCovariant: CovarCovariant<CovarBound> = TODO()

      class ContraContravariant<in T>
      class ContraBound
      class ContraClass {
        lateinit var contravariantField: ContraContravariant<ContraBound>
      }
      val testPropertyContravariant: ContraContravariant<ContraBound> = TODO()

      class NestedTypeArgWrapper<T>
      class NestedTypeArgInner<T>
      class NestedTypeArgBound
      class NestedTypeArgClass {
        lateinit var nestedGenField: NestedTypeArgWrapper<NestedTypeArgInner<NestedTypeArgBound>>
      }
      val testPropertyNestedTypeArgerics: NestedTypeArgWrapper<NestedTypeArgInner<NestedTypeArgBound>> = TODO()

      class MultiTypeArguments<K, out V, in T>
      class MultiKey
      class MultiValue
      class MultiTrigger
      class MultiClass {
        lateinit var multiField: MultiTypeArguments<MultiKey, MultiValue, MultiTrigger>
      }
      val testPropertyMultipleTypeArguments: MultiTypeArguments<MultiKey, MultiValue, MultiTrigger> = TODO()

      class ComplexOuter {
        class ComplexInner {
          class ComplexDeeplyNested<T>
        }
      }
      class ComplexBound
      interface ComplexInterface {
        fun complexMethod(): ComplexOuter.ComplexInner.ComplexDeeplyNested<ComplexBound?>?
      }
      val testPropertyComplex: ComplexOuter.ComplexInner.ComplexDeeplyNested<ComplexBound?>? = TODO()
    """
                  .trimIndent())

  @Test
  fun toType_simpleLevel() {
    configuration.withResolver(setOf(sharedSource)) { resolver ->
      val converted = resolveProperty(resolver, "testPropertySimple").toType()
      assertThat(converted).isEqualTo(Type(packageName = "com.foo", nameChain = listOf("Foo")))
    }
  }

  @Test
  fun toType_nestedLevel() {
    configuration.withResolver(setOf(sharedSource)) { resolver ->
      val converted = resolveProperty(resolver, "testPropertyNested").toType()
      assertThat(converted)
          .isEqualTo(Type(packageName = "com.foo", nameChain = listOf("Outer", "Inner")))
    }
  }

  @Test
  fun toType_nullable() {
    configuration.withResolver(setOf(sharedSource)) { resolver ->
      val converted = resolveProperty(resolver, "testPropertyNullable").toType()
      assertThat(converted)
          .isEqualTo(
              Type(packageName = "com.foo", nameChain = listOf("NullableFoo"), isNullable = true))
    }
  }

  @Test
  fun toType_typeArguments() {
    configuration.withResolver(setOf(sharedSource)) { resolver ->
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
  }

  @Test
  @OptIn(KspExperimental::class)
  fun toType_starProjection() {
    configuration.withResolver(setOf(sharedSource)) { resolver ->
      val converted = resolveProperty(resolver, "testPropertyStarProjection").toType()

      assertThat(converted)
          .isEqualTo(
              Type(
                  packageName = "com.foo",
                  nameChain = listOf("StarBox"),
                  typeArguments = listOf(Type.TypeArgument.Star)))
    }
  }

  @Test
  fun toType_covariant() {
    configuration.withResolver(setOf(sharedSource)) { resolver ->
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
  }

  @Test
  fun toType_contravariant() {
    configuration.withResolver(setOf(sharedSource)) { resolver ->
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
  }

  @Test
  fun toType_nestedTypeArguments() {
    configuration.withResolver(setOf(sharedSource)) { resolver ->
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
  }

  @Test
  fun toType_multipleTypeArguments() {
    configuration.withResolver(setOf(sharedSource)) { resolver ->
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
  }

  @Test
  fun toType_complex() {
    configuration.withResolver(setOf(sharedSource)) { resolver ->
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
}
