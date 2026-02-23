package com.jackbradshaw.backstab.ksp.parser.tests

import com.google.common.truth.Truth.assertThat
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.processing.SymbolProcessorProvider
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.jackbradshaw.backstab.core.CoreScope
import com.jackbradshaw.backstab.core.model.BackstabTarget
import com.jackbradshaw.oksp.model.SourceFile
import com.jackbradshaw.backstab.core.model.Type
import com.jackbradshaw.backstab.ksp.parser.Parser
import com.jackbradshaw.backstab.ksp.parser.ParserModule
import com.jackbradshaw.backstab.ksp.testing.SymbolProcessorTest
import dagger.Component
import javax.inject.Inject
import org.junit.Assert.assertThrows

class ParserImplTest(env: SymbolProcessorEnvironment) : SymbolProcessorTest(env) {

  @Inject lateinit var parser: Parser

  init {
    DaggerTestComponent.create().inject(this)
  }

  override fun supplyCases(): Map<String, (Resolver) -> Unit> =
      mapOf(
          "test_parse_create" to ::test_parse_create,
          "test_parseBuilder_customBuildFunctionName" to
              ::test_parseBuilder_customBuildFunctionName,
          "test_parseBuilder_standardBuildFunctionName" to
              ::test_parseBuilder_standardBuildFunctionName,
          "test_parseFactory_customFactoryFunctionName" to
              ::test_parseFactory_customFactoryFunctionName,
          "test_parseFactory_standardFactoryFunctionName" to
              ::test_parseFactory_standardFactoryFunctionName,
          "test_parse_noAnnotations" to ::test_parse_noAnnotations,
          "test_parse_componentOnly" to ::test_parse_componentOnly,
          "test_parse_backstabOnly" to ::test_parse_backstabOnly)

  private fun test_parse_create(resolver: Resolver) {
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

  private fun test_parseBuilder_customBuildFunctionName(resolver: Resolver) {
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

  private fun test_parseBuilder_standardBuildFunctionName(resolver: Resolver) {
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

  private fun test_parseFactory_customFactoryFunctionName(resolver: Resolver) {
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

  private fun test_parseFactory_standardFactoryFunctionName(resolver: Resolver) {
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

  private fun test_parse_noAnnotations(resolver: Resolver) {
    val declaration = resolveClass(resolver, "com.foo.NoAnnotationsInput")
    val exception =
        assertThrows(IllegalArgumentException::class.java) { parser.toBackstabTarget(declaration) }
    assertThat(exception)
        .hasMessageThat()
        .isEqualTo("Expected NoAnnotationsInput to be annotated with @Backstab.")
  }

  private fun test_parse_componentOnly(resolver: Resolver) {
    val declaration = resolveClass(resolver, "com.foo.ComponentOnlyInput")
    val exception =
        assertThrows(IllegalArgumentException::class.java) { parser.toBackstabTarget(declaration) }
    assertThat(exception)
        .hasMessageThat()
        .isEqualTo("Expected ComponentOnlyInput to be annotated with @Backstab.")
  }

  private fun test_parse_backstabOnly(resolver: Resolver) {
    val declaration = resolveClass(resolver, "com.foo.BackstabOnlyInput")
    val exception =
        assertThrows(IllegalArgumentException::class.java) { parser.toBackstabTarget(declaration) }
    assertThat(exception)
        .hasMessageThat()
        .isEqualTo("Expected BackstabOnlyInput to be annotated with @Component.")
  }

  private fun resolveClass(resolver: Resolver, name: String): KSClassDeclaration {
    val className = checkNotNull(resolver.getKSNameFromString(name)) { "Could not resolve $name" }

    return checkNotNull(resolver.getClassDeclarationByName(className)) { "Could not resolve $name" }
  }
}

@Component(modules = [ParserModule::class])
@CoreScope
interface TestComponent {
  fun inject(target: ParserImplTest)
}

class ParserImplTestProvider : SymbolProcessorProvider {
  override fun create(environment: SymbolProcessorEnvironment): SymbolProcessor {
    return ParserImplTest(environment)
  }
}
