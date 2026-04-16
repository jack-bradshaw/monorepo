# Kale

Kotlin Symbol Processing (KSP) testing infrastructure.

## Overview

Kale provides various tools for testing different aspects of KSP systems, specifically:

- [ResolverChassis](/first_party/kale/resolver/chassis/ResolverChassis.kt): Runs KSP and provides
  access to real
  [Resolver](https://github.com/google/ksp/blob/main/api/src/main/kotlin/com/google/devtools/ksp/processing/Resolver.kt)
  objects.
- [ProcessorRunner](/first_party/kale/processor/ProcessorRunner.kt): Runs your
  [SymbolProcessor](https://github.com/google/ksp/blob/main/api/src/main/kotlin/com/google/devtools/ksp/processing/SymbolProcessor.kt)
  on real KSP infrastructure.
- [ProviderRunner](/first_party/kale/provider/ProviderRunner.kt): Runs your
  [SymbolProcessorProvider](https://github.com/google/ksp/blob/main/api/src/main/kotlin/com/google/devtools/ksp/processing/SymbolProcessorProvider.kt)
  on real KSP infrastructure.

All three tools use real in-memory KSP execution to ensure your tests are a realistic representation
of production behavior.

## Guide

This guide walks through the usage of each tool.

## ResolverChassis

The `ResolverChassis` provides you with real KSP `Resolver` objects that you can use to access the
various types that are only available during KSP runs, such as
[KSAnnotated](https://github.com/google/ksp/blob/main/api/src/main/kotlin/com/google/devtools/ksp/symbol/KSAnnotated.kt),
[KSClassDeclaration](https://github.com/google/ksp/blob/main/api/src/main/kotlin/com/google/devtools/ksp/symbol/KSClassDeclaration.kt),
and
[KSNode](https://github.com/google/ksp/blob/main/api/src/main/kotlin/com/google/devtools/ksp/symbol/KSNode.kt).
This is useful when testing anything which requires a `Resolver` or a derivative type, because KSP
internals are notoriously difficult to mock and fake due to their complexity. With `ResolverChassis`
there is no need to mock/fake anything, which gives you both the safety of realistic testing and the
freedom of never maintaining complex mocks/fakes. Below is an end-to-end example of a test for a
util that uses KSP types:

```kotlin
import com.jackbradshaw.kale.kaleComponent
import com.jackbradshaw.kale.model.JvmSource
import com.jackbradshaw.kale.resolver.chassis.ResolverChassis

class Converter {
  fun getName(node: KSNode): String {
    return when (node) {
      is KSFile -> node.fileName
      is KSClassDeclaration -> node.simpleName.asString()
      is KSFunctionDeclaration -> node.simpleName.asString()
      else -> throw IllegalArgumentException("Unsupported node: $node")
    }
  }
}

@RunWith(JUnit4::class)
class ConverterTest {

  private val resolverChassis = kaleComponent().resolverChassis()

  private val converter = Converter()

  @After
  fun tearDown() {
    resolverChassis.close()
  }

  @Test
  fun convertsFileName() = runBlocking {
    val source = JvmSource(fileName = "TestFile", extension = "kt", contents = "class TestClass")

    resolverChassis.open(source).use { harness ->
      harness.withResolver { resolver ->
        val fileNode = resolver.getAllFiles().single()

        val convertedName = converter.getName(fileNode)

        assertThat(convertedName).isEqualTo("TestFile")
      }
    }
  }

  @Test
  fun convertsClassName() = runBlocking {
    val source = JvmSource(fileName = "TestFile", extension = "kt", contents = "class TestClass")

    resolverChassis.open(source).use { harness ->
      harness.withResolver { resolver ->
        val fileNode = resolver.getAllFiles().single()
        val classNode = fileNode.declarations.filterIsInstance<KSClassDeclaration>().single()

        val convertedName = converter.getName(classNode)

        assertThat(convertedName).isEqualTo("TestClass")
      }
    }
  }

  @Test
  fun convertsFunctionName() = runBlocking {
    val source = JvmSource(fileName = "TestFile", extension = "kt", contents = "fun testFunction() {}")

    resolverChassis.open(source).use { harness ->
      harness.withResolver { resolver ->
        val fileNode = resolver.getAllFiles().single()
        val functionNode = fileNode.declarations.filterIsInstance<KSFunctionDeclaration>().single()

        val convertedName = converter.getName(functionNode)

        assertThat(convertedName).isEqualTo("testFunction")
      }
    }
  }
}
```

Make sure to close the chassis at the end of the test, or use the
[ResolverTestRule](/first_party/kale/resolver/rule/ResolverTestRule.kt) to close it automatically,
for example:

```kotlin
import com.jackbradshaw.kale.model.JvmSource
import com.jackbradshaw.kale.resolver.rule.ResolverTestRule

class ConverterTest {

  @get:Rule val chassisRule = ResolverTestRule()

  private val converter = Converter()

  @Test
  fun convertsFileName() = runBlocking {
    val source = JvmSource(fileName = "TestFile", extension = "kt", contents = "class TestClass")

    chassisRule.resource.open(source).use { harness ->
      harness.withResolver { resolver ->
        val fileNode = resolver.getAllFiles().single()

        val convertedName = converter.getName(fileNode)

        assertThat(convertedName).isEqualTo("TestFile")
      }
    }
  }
}
```

Closure is necessary because of how the chassis works. Internally KSP has a complex single-threaded
architecture that prevents `Resolver` from simply being hoisted out of a KSP run and passed into the
test, so all test code that uses a `Resolver` must run in the actual KSP processing loop. The
chassis achieves this by running a real KSP job, holding it open indefinitely, and feeding your
`withResolver` blocks into the loop for processing. Closing the chassis allows the KSP job to end
which prevents resource leaks.

## Runners

Kale provides runners for executing KSP `SymbolProcessor`s and KSP `SymbolProcessorProvider`s on
real KSP infrastructure. This allows you to run integration tests against your complete KSP types,
for example:

```kotlin
import com.jackbradshaw.kale.kaleComponent
import com.jackbradshaw.kale.model.JvmSource

class KspTests {

  @Test
  fun testMyProcessor() = runBlocking {
    val source = JvmSource(fileName = "TestFile", extension = "kt", contents = "class TestClass")
    val processor: SymbolProcessor = MyProcessor()

    kaleComponent().processorRunner().runProcessor(processor, setOf(source))

    assertThat(processor.completedSuccessfully).isTrue()
  }

  @Test
  fun testMyProvider() = runBlocking {
    val source = JvmSource(fileName = "TestFile", extension = "kt", contents = "class TestClass")
    val provider = MyProvider()

    val runner = kaleComponent().providerRunner()
    runner.runProvider(provider, setOf(source))

    assertThat(provider.completedSuccessfully).isTrue()
  }
}
```

The runners support various options including passing through compiler options and specifying
[Versions](/first_party/kale/model/Versions.kt) information, for example:

```kotlin
import com.jackbradshaw.kale.kaleComponent
import com.jackbradshaw.kale.model.JvmSource
import com.jackbradshaw.kale.model.Versions

class KspTests {

  @Test
  fun testMyProvider() = runBlocking {
    val source = JvmSource(fileName = "TestFile", extension = "kt", contents = "class TestClass")
    val provider = MyProvider()

    kaleComponent().providerRunner().runProvider(provider, setOf(source), options = mapOf("useFoo", "false"), versions = Versions(jvmTarget = "1.7"))

    assertThat(provider.completedSuccessfully).isTrue()
  }
}
```

Check the KDoc for more details.

Note: `ProcessorRunner` accepts an already-instantiated processor and runs it using an anonymous
provider, so the processor cannot require any constructor arguments that can only come from KSP
(e.g. the KSP
[SymbolProcessorEnvironment](https://github.com/google/ksp/blob/main/api/src/main/kotlin/com/google/devtools/ksp/processing/SymbolProcessorEnvironment.kt)).
Any processors that require these arguments should be tested with the `ProviderRunner` instead so
you can instantiate them with the regular KSP types. For example:

```kotlin
import com.jackbradshaw.kale.kaleComponent
import com.jackbradshaw.kale.model.JvmSource
import com.jackbradshaw.kale.model.Versions

class KspTests {

  @Test
  fun testMyProcessor() = runBlocking {
    val source = JvmSource(fileName = "TestFile", extension = "kt", contents = "class TestClass")

    var processor: MyProcessor? = null
    val provider = object : SymbolProcessorProvider {
      override fun create(environment: SymbolProcessorEnvironment): SymbolProcessor {
        processor = MyProcessor(environment)
        return processor!!
      }
    }

    kaleComponent().providerRunner().runProvider(provider, setOf(source))

    assertThat(processor!!.completedSuccessfully).isTrue()
  }
}
```

## Caveats

Kale discovers and uses the classpath of the enclosing JVM process in every KSP run. If you need a
custom classpath, please file a feature request.

## Modularity

Interface-based programming is used extensively throughout this package such that most tools can be
completely reimplemented by third parties without compromising compatibility with the broader tool
system. For example, you could implement your own `ResolverChassis` and it should work with all
downstream tests. For convenience, abstract tests are provided for all tools, and you can check your
implementations against them. Follow the example in
[ResolverChassisTest](/first_party/kale/resolver/chassis/ResolverChassisTest.kt).

## Other Libraries

Kale is not the only library for testing Kotlin compilation, with
[Kotlin Compile Testing](https://github.com/tschuchortdev/kotlin-compile-testing) being a notable
alternative. Kale is primarily an opinionated API that performs the three outlined tasks well:
Running KSP `SymbolProcessor`s, running KSP `SymbolProcessorProvider`s, and accessing a KSP
`Resolver`. If you need more general compilation infrastructure, other libraries may work better,
but if you need these three tasks reliably with solid documentation, then consider Kale.

## Issues

Issues relating to this package and its subpackages are tagged with `kale`.

## Contributions

Third-party contributions are accepted.
