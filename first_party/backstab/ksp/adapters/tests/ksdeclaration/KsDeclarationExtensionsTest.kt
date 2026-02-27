package com.jackbradshaw.backstab.ksp.adapters.tests.ksdeclaration

import com.google.devtools.ksp.processing.Resolver
import com.google.common.truth.Truth.assertThat
import com.jackbradshaw.oksp.application.Application
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.jackbradshaw.backstab.ksp.adapters.nameChain
import com.jackbradshaw.backstab.ksp.testing.SymbolProcessorTest

class KsDeclarationExtensionsTest : SymbolProcessorTest() {

  override fun supplyCases(): Map<String, (Resolver) -> Unit> =
      mapOf(
          "nameChain_singleLevel" to ::test_nameChain_singleLevel,
          "nameChain_multipleLevels" to ::test_nameChain_multipleLevels,
          "nameChain_deepNesting" to ::test_nameChain_deepNesting)

  private fun test_nameChain_singleLevel(resolver: Resolver) {
    val foo = resolveClass(resolver, "com.foo.Foo")

    val converted = foo.nameChain()

    assertThat(converted).isEqualTo(listOf("Foo"))
  }

  private fun test_nameChain_multipleLevels(resolver: Resolver) {
    val leaf = resolveClass(resolver, "com.foo.Outer.Inner.Leaf")

    val converted = leaf.nameChain()

    assertThat(converted).isEqualTo(listOf("Outer", "Inner", "Leaf"))
  }

  private fun test_nameChain_deepNesting(resolver: Resolver) {
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

  private fun resolveClass(resolver: Resolver, name: String): KSClassDeclaration {
    val targetName = checkNotNull(resolver.getKSNameFromString(name)) { "Could not find $name" }

    return checkNotNull(resolver.getClassDeclarationByName(targetName)) {
      "Could not resolve $name"
    }
  }

  class TestApplication : Application by KsDeclarationExtensionsTest()
}
