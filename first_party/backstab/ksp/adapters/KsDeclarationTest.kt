package com.jackbradshaw.backstab.ksp.adapters

import com.google.common.truth.Truth.assertThat
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.jackbradshaw.kale.ksprunner.JvmSource
import com.jackbradshaw.kale.resolver.rule.ResolverTestRule
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class KsDeclarationTest {

  @get:Rule val resolverRule = ResolverTestRule()

  @Test
  fun nameChain_singleLevel() {
    val source =
        JvmSource(
            packageName = "com.foo",
            fileName = "nameChain_singleLevel_input",
            extension = "kt",
            contents =
                """
        package com.foo
        class Foo
      """
                    .trimIndent())

    val harness = resolverRule.open(source)

    harness.withResolver { resolver ->
      val foo = resolveClass(resolver, "com.foo.Foo")
      val converted = foo.nameChain()
      assertThat(converted).isEqualTo(listOf("Foo"))
    }
  }

  @Test
  fun nameChain_multipleLevels() {
    val source =
        JvmSource(
            packageName = "com.foo",
            fileName = "nameChain_multipleLevels_input",
            extension = "kt",
            contents =
                """
        package com.foo
        class Outer {
          class Inner {
            class Leaf
          }
        }
      """
                    .trimIndent())

    val harness = resolverRule.open(source)

    harness.withResolver { resolver ->
      val leaf = resolveClass(resolver, "com.foo.Outer.Inner.Leaf")
      val converted = leaf.nameChain()
      assertThat(converted).isEqualTo(listOf("Outer", "Inner", "Leaf"))
    }
  }

  @Test
  fun nameChain_deepNesting() {
    val source =
        JvmSource(
            packageName = "com.foo",
            fileName = "nameChain_deepNesting_input",
            extension = "kt",
            contents =
                """
        package com.foo
        class Level1 {
          class Level2 {
            class Level3 {
              class Level4 {
                class Level5 {
                  class Level6 {
                    class Level7 {
                      class Level8 {
                        class Level9 {
                          class Level10
                        }
                      }
                    }
                  }
                }
              }
            }
          }
        }
      """
                    .trimIndent())

    val harness = resolverRule.open(source)

    harness.withResolver { resolver ->
      val clazz =
          resolveClass(
              resolver,
              "com.foo.Level1.Level2.Level3.Level4.Level5.Level6.Level7.Level8.Level9.Level10")

      val converted = clazz.nameChain()

      assertThat(converted)
          .isEqualTo(
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
                  "Level10"))
    }
  }

  private fun resolveClass(resolver: Resolver, name: String): KSClassDeclaration {
    val targetName = checkNotNull(resolver.getKSNameFromString(name)) { "Could not convert $name" }
    return checkNotNull(resolver.getClassDeclarationByName(targetName)) {
      "Could not resolve $name"
    }
  }
}
