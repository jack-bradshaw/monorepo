package com.jackbradshaw.backstab.ksp.parser.tests

import com.google.common.truth.Truth.assertThat
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.jackbradshaw.backstab.core.model.BackstabTarget
import com.jackbradshaw.backstab.core.model.Type
import com.jackbradshaw.backstab.ksp.parser.Parser
import com.jackbradshaw.backstab.ksp.parser.ParserImpl
import com.jackbradshaw.kale.ksprunner.JvmSource
import com.jackbradshaw.kale.resolver.rule.ResolverTestRule
import com.jackbradshaw.oksp.model.SourceFile
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertThrows
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ParserImplTest {

  @get:Rule val configuration = ResolverTestRule()

  private val parser: Parser = ParserImpl()

  val sources =
      listOf(
          JvmSource(
              packageName = "com.foo",
              fileName = "parse_create_input",
              extension = "kt",
              contents =
                  """
        package com.foo
        import com.jackbradshaw.backstab.core.annotations.Backstab
        import dagger.Component

        @Backstab @Component interface ComponentCreate
      """
                      .trimIndent()),
          JvmSource(
              packageName = "com.foo",
              fileName = "parse_builder_custom_input",
              extension = "kt",
              contents =
                  """
        package com.foo
        import com.jackbradshaw.backstab.core.annotations.Backstab
        import dagger.Component
        import javax.inject.Named

        @Backstab
        @Component
        interface ComponentBuilderCustom {
          @Component.Builder
          interface Builder {
            fun setFoo(@Named("foo") foo: String): Builder
            fun execute(): ComponentBuilderCustom
          }
        }
      """
                      .trimIndent()),
          JvmSource(
              packageName = "com.foo",
              fileName = "parse_builder_standard_input",
              extension = "kt",
              contents =
                  """
        package com.foo
        import com.jackbradshaw.backstab.core.annotations.Backstab
        import dagger.Component
        import javax.inject.Named

        @Backstab
        @Component
        interface ComponentBuilderStandard {
          @Component.Builder
          interface Builder {
            fun setFoo(@Named("foo") foo: String): Builder
            fun build(): ComponentBuilderStandard
          }
        }
      """
                      .trimIndent()),
          JvmSource(
              packageName = "com.foo",
              fileName = "parse_factory_custom_input",
              extension = "kt",
              contents =
                  """
        package com.foo
        import com.jackbradshaw.backstab.core.annotations.Backstab
        import dagger.Component
        import javax.inject.Named

        @Backstab
        @Component
        interface ComponentFactoryCustom {
          @Component.Factory
          interface Factory {
            fun createIt(@Named("foo") foo: String): ComponentFactoryCustom
          }
        }
      """
                      .trimIndent()),
          JvmSource(
              packageName = "com.foo",
              fileName = "parse_factory_standard_input",
              extension = "kt",
              contents =
                  """
        package com.foo
        import com.jackbradshaw.backstab.core.annotations.Backstab
        import dagger.Component
        import javax.inject.Named

        @Backstab
        @Component
        interface ComponentFactoryStandard {
          @Component.Factory
          interface Factory {
            fun factory(@Named("foo") foo: String): ComponentFactoryStandard
          }
        }
      """
                      .trimIndent()),
          JvmSource(
              packageName = "com.foo",
              fileName = "parse_no_annotations_input",
              extension = "kt",
              contents =
                  """
        package com.foo
        class NoAnnotationsInput
      """
                      .trimIndent()),
          JvmSource(
              packageName = "com.foo",
              fileName = "parse_component_only_input",
              extension = "kt",
              contents =
                  """
        package com.foo
        import dagger.Component
        @Component interface ComponentOnlyInput
      """
                      .trimIndent()),
          JvmSource(
              packageName = "com.foo",
              fileName = "parse_backstab_only_input",
              extension = "kt",
              contents =
                  """
        package com.foo
        import com.jackbradshaw.backstab.core.annotations.Backstab
        @Backstab interface BackstabOnlyInput
      """
                      .trimIndent()))

  @Test
  fun test_parse_create() {
    evaluateAgainstResolver(sources) { resolver ->
      val declaration = resolveClass(resolver, "com.foo.ComponentCreate")
      val target = parser.toBackstabTarget(declaration)

      val expected =
          BackstabTarget(
              header = SourceFile(packageName = "com.foo", fileName = "parse_create_input"),
              component =
                  BackstabTarget.Component(
                      packageName = "com.foo", nameChain = listOf("ComponentCreate")),
              instantiator = BackstabTarget.ComponentInstantiator.CreateFunction)
      assertThat(target).isEqualTo(expected)
    }
  }

  @Test
  fun test_parseBuilder_customBuildFunctionName() {
    evaluateAgainstResolver(sources) { resolver ->
      val declaration = resolveClass(resolver, "com.foo.ComponentBuilderCustom")
      val target = parser.toBackstabTarget(declaration)

      val expected =
          BackstabTarget(
              header = SourceFile(packageName = "com.foo", fileName = "parse_builder_custom_input"),
              component =
                  BackstabTarget.Component(
                      packageName = "com.foo", nameChain = listOf("ComponentBuilderCustom")),
              instantiator =
                  BackstabTarget.ComponentInstantiator.BuilderInterface(
                      setters =
                          listOf(
                              BackstabTarget.ComponentInstantiator.BuilderInterface.SetterFunction(
                                  name = "setFoo",
                                  type = Type(packageName = "kotlin", nameChain = listOf("String")),
                                  qualifier = BackstabTarget.Qualifier.Named("foo"))),
                      buildFunction =
                          BackstabTarget.ComponentInstantiator.BuilderInterface.BuildFunction(
                              name = "execute",
                              returnType =
                                  Type(
                                      packageName = "com.foo",
                                      nameChain = listOf("ComponentBuilderCustom")))))
      assertThat(target).isEqualTo(expected)
    }
  }

  @Test
  fun test_parseBuilder_standardBuildFunctionName() {
    evaluateAgainstResolver(sources) { resolver ->
      val declaration = resolveClass(resolver, "com.foo.ComponentBuilderStandard")
      val target = parser.toBackstabTarget(declaration)

      val expected =
          BackstabTarget(
              header =
                  SourceFile(packageName = "com.foo", fileName = "parse_builder_standard_input"),
              component =
                  BackstabTarget.Component(
                      packageName = "com.foo", nameChain = listOf("ComponentBuilderStandard")),
              instantiator =
                  BackstabTarget.ComponentInstantiator.BuilderInterface(
                      setters =
                          listOf(
                              BackstabTarget.ComponentInstantiator.BuilderInterface.SetterFunction(
                                  name = "setFoo",
                                  type = Type(packageName = "kotlin", nameChain = listOf("String")),
                                  qualifier = BackstabTarget.Qualifier.Named("foo"))),
                      buildFunction =
                          BackstabTarget.ComponentInstantiator.BuilderInterface.BuildFunction(
                              name = "build",
                              returnType =
                                  Type(
                                      packageName = "com.foo",
                                      nameChain = listOf("ComponentBuilderStandard")))))
      assertThat(target).isEqualTo(expected)
    }
  }

  @Test
  fun test_parseFactory_customFactoryFunctionName() {
    evaluateAgainstResolver(sources) { resolver ->
      val declaration = resolveClass(resolver, "com.foo.ComponentFactoryCustom")
      val target = parser.toBackstabTarget(declaration)

      val expected =
          BackstabTarget(
              header = SourceFile(packageName = "com.foo", fileName = "parse_factory_custom_input"),
              component =
                  BackstabTarget.Component(
                      packageName = "com.foo", nameChain = listOf("ComponentFactoryCustom")),
              instantiator =
                  BackstabTarget.ComponentInstantiator.FactoryFunction(
                      name = "createIt",
                      parameters =
                          listOf(
                              BackstabTarget.ComponentInstantiator.FactoryFunction.Parameter(
                                  type = Type(packageName = "kotlin", nameChain = listOf("String")),
                                  qualifier = BackstabTarget.Qualifier.Named("foo")))))
      assertThat(target).isEqualTo(expected)
    }
  }

  @Test
  fun test_parseFactory_standardFactoryFunctionName() {
    evaluateAgainstResolver(sources) { resolver ->
      val declaration = resolveClass(resolver, "com.foo.ComponentFactoryStandard")
      val target = parser.toBackstabTarget(declaration)

      val expected =
          BackstabTarget(
              header =
                  SourceFile(packageName = "com.foo", fileName = "parse_factory_standard_input"),
              component =
                  BackstabTarget.Component(
                      packageName = "com.foo", nameChain = listOf("ComponentFactoryStandard")),
              instantiator =
                  BackstabTarget.ComponentInstantiator.FactoryFunction(
                      name = "factory",
                      parameters =
                          listOf(
                              BackstabTarget.ComponentInstantiator.FactoryFunction.Parameter(
                                  type = Type(packageName = "kotlin", nameChain = listOf("String")),
                                  qualifier = BackstabTarget.Qualifier.Named("foo")))))
      assertThat(target).isEqualTo(expected)
    }
  }

  @Test
  fun test_parse_noAnnotations() {
    evaluateAgainstResolver(sources) { resolver ->
      val declaration = resolveClass(resolver, "com.foo.NoAnnotationsInput")
      val exception =
          assertThrows(IllegalArgumentException::class.java) {
            parser.toBackstabTarget(declaration)
          }
      assertThat(exception)
          .hasMessageThat()
          .isEqualTo("Expected NoAnnotationsInput to be annotated with @Backstab.")
    }
  }

  @Test
  fun test_parse_componentOnly() {
    evaluateAgainstResolver(sources) { resolver ->
      val declaration = resolveClass(resolver, "com.foo.ComponentOnlyInput")
      val exception =
          assertThrows(IllegalArgumentException::class.java) {
            parser.toBackstabTarget(declaration)
          }
      assertThat(exception)
          .hasMessageThat()
          .isEqualTo("Expected ComponentOnlyInput to be annotated with @Backstab.")
    }
  }

  @Test
  fun test_parse_backstabOnly() {
    evaluateAgainstResolver(sources) { resolver ->
      val declaration = resolveClass(resolver, "com.foo.BackstabOnlyInput")
      val exception =
          assertThrows(IllegalArgumentException::class.java) {
            parser.toBackstabTarget(declaration)
          }
      assertThat(exception)
          .hasMessageThat()
          .isEqualTo("Expected BackstabOnlyInput to be annotated with @Component.")
    }
  }

  private fun resolveClass(resolver: Resolver, name: String): KSClassDeclaration {
    val className = checkNotNull(resolver.getKSNameFromString(name)) { "Could not resolve $name" }
    return checkNotNull(resolver.getClassDeclarationByName(className)) { "Could not resolve $name" }
  }

  private fun evaluateAgainstResolver(sources: List<JvmSource>, block: (Resolver) -> Unit) =
      runBlocking {
        configuration.get().open(sources).withResolver(block)
      }
}
