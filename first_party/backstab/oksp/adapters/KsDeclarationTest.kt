package com.jackbradshaw.backstab.oksp.adapters

import com.google.common.truth.Truth.assertThat
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.jackbradshaw.kale.model.Source
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
        Source(
            packageName = "com.foo",
            fileName = "nameChain_singleLevel_input",
            extension = "kt",
            contents =
                """
        package com.foo
        class Foo
      """
                    .trimIndent())

    evaluateAgainstResolver(setOf(source)) { resolver ->
      val foo = resolveClass(resolver, "com.foo.Foo")
      val converted = foo.nameChain()
      assertThat(converted).isEqualTo(listOf("Foo"))
    }
  }

  @Test
  fun nameChain_multipleLevels() {
    val source =
        Source(
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

    evaluateAgainstResolver(setOf(source)) { resolver ->
      val leaf = resolveClass(resolver, "com.foo.Outer.Inner.Leaf")
      val converted = leaf.nameChain()
      assertThat(converted).isEqualTo(listOf("Outer", "Inner", "Leaf"))
    }
  }

  @Test
  fun nameChain_deepNesting() {
    val source =
        Source(
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

    evaluateAgainstResolver(setOf(source)) { resolver ->
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


  private fun evaluateAgainstResolver(sources: Set<com.jackbradshaw.kale.model.Source>, block: (com.google.devtools.ksp.processing.Resolver) -> Unit) {
    kotlinx.coroutines.runBlocking {
      val versions = com.jackbradshaw.kale.model.Versions()
      resolverRule.get().open(sources.map { com.jackbradshaw.kale.model.Source(it.fileName, it.extension, it.packageName, it.contents) }.toSet(), versions, emptyMap<String, String>()).withResolver(block)
    }
  }



}
