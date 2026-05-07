package com.jackbradshaw.backstab.oksp.adapters

import com.google.common.truth.Truth.assertThat
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.jackbradshaw.backstab.core.model.BackstabTarget
import com.jackbradshaw.backstab.core.model.Type
import com.jackbradshaw.kale.resolver.rule.ResolverTestRule
import com.jackbradshaw.obelisk.core.model.Source
import org.junit.Assert.assertThrows
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class BackstabInflowAdapterTest {

  @get:Rule val configuration = ResolverTestRule()

  private val adapter = BackstabInflowAdapter()

  val sources =
      listOf(
          Source(
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
          Source(
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
          Source(
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
          Source(
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
          Source(
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
          Source(
              packageName = "com.foo",
              fileName = "parse_no_annotations_input",
              extension = "kt",
              contents =
                  """
        package com.foo
        class NoAnnotationsInput
      """
                      .trimIndent()),
          Source(
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
          Source(
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
      val ingestion = adapter.ingest(setOf(declaration))

      val expected =
          BackstabTarget(
              header = Source(packageName = "com.foo", fileName = "parse_create_input"),
              component =
                  BackstabTarget.Component(
                      packageName = "com.foo", nameChain = listOf("ComponentCreate")),
              instantiator = BackstabTarget.ComponentInstantiator.CreateFunction)

      assertThat(ingestion.translated[declaration]).containsExactly(expected)
    }
  }

  @Test
  fun test_parseBuilder_customBuildFunctionName() {
    evaluateAgainstResolver(sources) { resolver ->
      val declaration = resolveClass(resolver, "com.foo.ComponentBuilderCustom")
      val ingestion = adapter.ingest(setOf(declaration))

      val expected =
          BackstabTarget(
              header = Source(packageName = "com.foo", fileName = "parse_builder_custom_input"),
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
      assertThat(ingestion.translated[declaration]).containsExactly(expected)
    }
  }

  @Test
  fun test_parseBuilder_standardBuildFunctionName() {
    evaluateAgainstResolver(sources) { resolver ->
      val declaration = resolveClass(resolver, "com.foo.ComponentBuilderStandard")
      val ingestion = adapter.ingest(setOf(declaration))

      val expected =
          BackstabTarget(
              header = Source(packageName = "com.foo", fileName = "parse_builder_standard_input"),
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
      assertThat(ingestion.translated[declaration]).containsExactly(expected)
    }
  }

  @Test
  fun test_parseFactory_customFactoryFunctionName() {
    evaluateAgainstResolver(sources) { resolver ->
      val declaration = resolveClass(resolver, "com.foo.ComponentFactoryCustom")
      val ingestion = adapter.ingest(setOf(declaration))

      val expected =
          BackstabTarget(
              header = Source(packageName = "com.foo", fileName = "parse_factory_custom_input"),
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
      assertThat(ingestion.translated[declaration]).containsExactly(expected)
    }
  }

  @Test
  fun test_parseFactory_standardFactoryFunctionName() {
    evaluateAgainstResolver(sources) { resolver ->
      val declaration = resolveClass(resolver, "com.foo.ComponentFactoryStandard")
      val ingestion = adapter.ingest(setOf(declaration))

      val expected =
          BackstabTarget(
              header = Source(packageName = "com.foo", fileName = "parse_factory_standard_input"),
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
      assertThat(ingestion.translated[declaration]).containsExactly(expected)
    }
  }

  @Test
  fun test_parse_noAnnotations() {
    evaluateAgainstResolver(sources) { resolver ->
      val declaration = resolveClass(resolver, "com.foo.NoAnnotationsInput")
      val ingestion = adapter.ingest(setOf(declaration))

      assertThat(ingestion.translated).isEmpty()
      assertThat(ingestion.unused).isEmpty()
    }
  }

  @Test
  fun test_parse_componentOnly() {
    evaluateAgainstResolver(sources) { resolver ->
      val declaration = resolveClass(resolver, "com.foo.ComponentOnlyInput")
      val ingestion = adapter.ingest(setOf(declaration))

      assertThat(ingestion.translated).isEmpty()
      assertThat(ingestion.unused).isEmpty()
    }
  }

  @Test
  fun test_parse_backstabOnly() {
    evaluateAgainstResolver(sources) { resolver ->
      val declaration = resolveClass(resolver, "com.foo.BackstabOnlyInput")
      val exception =
          assertThrows(IllegalArgumentException::class.java) { adapter.ingest(setOf(declaration)) }
      assertThat(exception)
          .hasMessageThat()
          .isEqualTo("Expected BackstabOnlyInput to be annotated with @Component.")
    }
  }

  private fun resolveClass(resolver: Resolver, name: String): KSClassDeclaration {
    val className = checkNotNull(resolver.getKSNameFromString(name)) { "Could not resolve $name" }
    return checkNotNull(resolver.getClassDeclarationByName(className)) { "Could not resolve $name" }
  }

  private fun evaluateAgainstResolver(sources: List<Source>, block: (Resolver) -> Unit) {
    kotlinx.coroutines.runBlocking {
      val versions = com.jackbradshaw.kale.model.Versions()
      configuration
          .get()
          .open(
              sources
                  .map {
                    com.jackbradshaw.kale.model.Source(
                        it.fileName, it.extension, it.packageName, it.contents)
                  }
                  .toSet(),
              versions,
              emptyMap<String, String>())
          .withResolver(block)
    }
  }
}
