# OKSP

An asynchronous, reactive application framework for Kotlin Symbol Processing (KSP).

## Overview

OKSP abstracts away the rigid, synchronous, round-based complexities of native KSP. It replaces the
traditional compiler plugin architecture with a standard `Application` lifecycle (`onCreate`,
`onDestroy`) and provides a convenient, asynchronous flow-based API (`KspService`) for managing
compiler rounds and safely interacting with KSP internals.

## The Problem: Synchronous Coupling

Implementing KSP natively forces the program structure into an inherently synchronous, round-based
loop. This creates several architectural friction points:

1. **Entry Point Coupling**: It tightly couples the system's entry point to the data inflow.
2. **Concurrency Restrictions**: It requires massive boilerplate and custom orchestration to bridge
   simple reactive flows into a rigid compiler framework.
3. **Loss of Focus**: Downstream consumers are burdened with managing specific compiler rounds,
   state propagation, and synchronous teardown logic rather than focusing on actual symbol analysis
   or code generation.

Because real systems are generally far more complex than simple linear symbol processors, native KSP
restricts the broader program architecture.

## The Solution: An Abstract Application Root

OKSP solves this by completely abstracting the actual KSP foundation. Instead of implementing a
native `SymbolProcessor` or dealing with raw processing loops, consumers simply implement a generic
`Application` interface.

```kotlin
interface OkspApplication {
  suspend fun onCreate(component: KspComponent)
  suspend fun onDestroy()
}
```

This focuses on an abstract lifecycle that can do anything. To interact with KSP, OKSP injects a
Dagger `KspComponent` into the `onCreate` method. This component supplies a `KspService`—a highly
robust, asynchronous API that completely hides the details of processing rounds behind convenient
Kotlin Flows.

By managing the lifecycle and compiler synchronization internally, OKSP allows downstream systems to
write advanced concurrent programming logic without ever touching the native KSP limitations.

## Usage

Creating an OKSP Application is straightforward:

1. Create a class that implements `Application`.
2. Use the injected `KspService` to coordinate with the compiler.
3. Declare your application in
   `META-INF/services/com.jackbradshaw.oksp.application.OkspApplication`.
4. Ensure the OKSP processor `com.jackbradshaw.oksp.host.HostImpl` is declared in
   `META-INF/services/com.google.devtools.ksp.processing.SymbolProcessorProvider` (OKSP provides
   this by default if you depend on the predefined `//first_party/oksp/host:host_impl_declared`
   target).

```kotlin
import com.jackbradshaw.oksp.application.OkspApplication
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class MyAnalyzerApplication : OkspApplication {

  override suspend fun onCreate(component: OkspApplication.KspComponent) {
    val service = component.kspService()

    // Launch background asynchronous work
    CoroutineScope(Dispatchers.IO).launch {
      // Begin processing
      service.startProcessing()

      // Suspend until the first round begins
      service.onEachRoundStart().first()

      // Interact safely with the native KSP Context (Resolver + Environment)
      service.withContext { context ->
        val filePaths = context.resolver.getAllFiles().map { it.filePath }
        println("Discovered files: $filePaths")
      }

      // Allow the round to natively complete
      service.completeRound()

      // Unblock the native KSP process to eventually terminate
      service.allowTermination()
    }
  }

  override suspend fun onDestroy() {
    // Teardown connections, flush caches, release locks, etc.
  }
}
```

The framework securely orchestrates the KSP threads under the hood, ensuring the `Resolver` is
accessed safely and the native compiler process is kept alive until `allowTermination()` is
explicitly called.

## Testing

OKSP provides the `ApplicationChassis` to easily run in-memory, end-to-end tests of your
`Application`. Under the hood, it uses the Kale testing framework to spin up a real KSP compiler
pass and feed it your source code.

```kotlin
import com.jackbradshaw.oksp.testing.application.chassis.ApplicationChassis
import com.jackbradshaw.oksp.testing.application.chassis.applicationChassisComponent
import com.jackbradshaw.kale.provider.providerRunnerComponent
import com.jackbradshaw.kale.model.Source
import com.jackbradshaw.kale.model.Result
import com.jackbradshaw.kale.model.Log
import com.google.common.truth.Truth.assertThat
import org.junit.Test
import kotlinx.coroutines.runBlocking

class MyAnalyzerApplicationTest {

  private val chassis: OkspApplicationChassis = applicationChassisComponent(providerRunnerComponent()).chassis()

  @Test
  fun testMyApplication() = runBlocking {
    val source = Source(packageName = "com.test", fileName = "TestFile", contents = "class Target")
    val application = MyAnalyzerApplication()

    val result = chassis.run(application, setOf(source))

    // Verify successful execution, generated files, logs, etc.
    assertThat(result).isInstanceOf(Result.Success::class.java)
    val infoLogs = result.logs.filterIsInstance<Log.Info>()
    assertThat(infoLogs.map { it.message }).contains("Application started")

    // Alternatively verify failure, errors, logs, etc.
    assertThat(result).isInstanceOf(Result.Failure::class.java)
    val resultCast = result as Result.Failure
    assertThat(resultCast.error).isInstanceOf(RuntimeException::class.java)
    assertThat(resultCast.error?.message).isEqualTo("foo")
    val errorLogs = result.logs.filterIsInstance<Log.Error>()
    assertThat(errorLogs.map { it.message }).contains("Processing aborted")
  }
}
```

This isolates your KSP logic so you can verify that files were generated, logs were written, and
that no exceptions were thrown during the asynchronous lifecycle without mocking native KSP
internals.

## Modularity

Interface-based programming is used extensively throughout this package such that most components
can be completely reimplemented by third parties without compromising compatibility with the broader
framework. For example, you could implement your own `KspService` or `Host` and they will work
seamlessly with downstream applications. For convenience, abstract tests are provided for core
components, and you can check your custom implementations against them. Follow the examples in
[KspServiceTest](/first_party/oksp/service/KspServiceTest.kt) or
[HostTest](/first_party/oksp/host/HostTest.kt).

Additionally, OKSP is heavily Dagger-based. The `KspComponent` injected into the application serves
as an integration point that can be extended or mapped to inject your own custom dependencies,
ensuring clean, modular integration with complex DI hierarchies.

## Issues

Issues relating to this package and its subpackages are tagged with `oksp`.

## Contributions

Contributions from third parties are accepted.
