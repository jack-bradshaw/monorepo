# Backstab Architecture

## Scope

This document details the architectural decisions, design patterns, and tradeoffs for the `Backstab` dependency injection framework. It serves as the primary reference for understanding the system's structure and the reasoning behind it.

## Overview

Backstab is a light-weight dependency injection framework built on KSP (Kotlin Symbol Processing). It uses compile-time annotation processing to generate Dagger-like component implementations.

The architecture follows a strict Clean Architecture approach, separating the KSP compiler framework (Drivers/Frameworks layer) from the core business logic (Use Cases/Entities layer).

```mermaid
graph TD
    Entrypoint[Entrypoint (KSP)] -->|Calls| Processor[Processor (Business Logic)]
    Processor -->|Maps| Model[Domain Model]
    Processor -->|Calls| Generator[Generator]
    Processor -->|Calls| Writer[Writer]
    Generator -->|Produces| FileSpec[FileSpec (KotlinPoet)]
    Writer -->|Persists| Disk[Disk / FileSystem]
    Entrypoint -.->|Provides| Resolver[KSP Resolver]
    Entrypoint -.->|Provides| Logger[KSP Logger]
```

## Architectural Decisions

The following architectural decisions have been made to ensure testability, scalability, and adherence to clean architecture principles.

### Standard: Separation of Entrypoint and Processor

The system is divided into an `Entrypoint` layer and a `Processor` layer.

- Entrypoint: Implements the framework-specific interface `SymbolProcessor`. Handles environment setup, argument parsing, and error catching.
- Processor: Implements the domain-specific interface `BackstabProcessor`. Contains the core logic for orchestrating generation.

_Rationale_:

This decoupling allows the core business logic to be unit-tested without instantiating the heavy KSP `SymbolProcessorEnvironment`. It also allows the processor logic to be ports to other compiler plugins (e.g. direct CLI, compiler embeddable) with minimal changes.

_Tradeoffs_:

- _Pros_: High testability, clear separation of concerns, framework independence.
- _Cons_: Slight increase in boilerplate (two classes instead of one).

_Example_:

```kotlin
// Entrypoint (KSP adapter)
// Factory
class BackstabProcessorFactory : SymbolProcessorProvider {
  override fun create(env: SymbolProcessorEnvironment) = BackstabEntrypoint(env)
}

// Entrypoint (KSP adapter)
class BackstabEntrypoint(env: SymbolProcessorEnvironment) : SymbolProcessor {
  override fun process(resolver: Resolver): List<KSAnnotated> {
    val processor = BackstabProcessorImpl(...)
    // Bridges to async world
    runBlocking { processor.createMetaComponents(validSymbols) }
    return emptyList()
  }
}

// Processor (Pure business logic)
class BackstabProcessorImpl(...) : BackstabProcessor {
  override suspend fun createMetaComponents(components: List<KSClassDeclaration>) {
    // ...
  }
}
```

_Rejected Options_:

- _Monolithic Processor_: Implementing logic directly in `SymbolProcessor.process()` was rejected because it tightly couples business logic with KSP internals, making unit testing difficult (requires `kotlin-compile-testing` or integration tests for every small logic change).

### Practice: Async-First Internal APIs

All internal interfaces (`Processor`, `Generator`, `Writer`) expose `suspend` functions, even though KSP's `process()` method is synchronous.

_Rationale_:

The system "bridges" the synchronous KSP world to the asynchronous internal world using `runBlocking` at the Entrypoint.

1. Future Proofing: If KSP (or a future compiler API) supports asynchronous processing, the core logic is ready.
1. Concurrency: Allows for parallelization of independent steps (e.g. generating multiple components at once) using `coroutineScope` inside the logic layer, safely managed within the synchronous blocking call.

_Tradeoffs_:

- _Pros_: Ready for parallelism, explicitly models IO-bound operations (writing files) as suspending.
- _Cons_: Requires `runBlocking` bridge in the Entrypoint; developers must understand Coroutines.

_Example_:

```kotlin
interface Generator {
  // Suspend allows for future IO or parallel processing without breaking API
  suspend fun generate(model: BackstabComponent): FileSpec
}
```

_Rejected Options_:

- _Synchronous Interfaces_: Rejected because it locks the architecture into the current threaded model of KSP, making it harder to optimize performance later without breaking internal APIs.

### Standard: KSP as a Data Source

The KSP `Resolver` and `KSAnnotated` symbols are treated as a transient Data Source.

- The `Entrypoint` or `Processor` immediately maps KSP symbols (e.g. `KSClassDeclaration`) to our own domain `Model` (data classes).
- Downstream components (`Generator`) operate _only_ on the Domain Model, never on KSP symbols.

_Rationale_:

KSP `Resolver` instances are ephemeral and valid only for a single round of processing. Passing them deep into the generator logic risks accessing invalid symbols or leaking compiler implementation details into the code generation logic.

_Tradeoffs_:

- _Pros_: Safety (impossible to access invalid symbols later), clear boundaries, simplified Generator testing (POJO inputs).
- _Cons_: Overhead of mapping and maintaining a parallel Model hierarchy.

_Example_:

```kotlin
// Map KSP symbol to Domain Model immediately
val model = BackstabComponent(
    packageName = component.packageName.asString(),
    simpleName = component.simpleName.asString(),
    builderBindings = findBuilderBindings(component)
)

// Generator takes Model, NOT KSClassDeclaration
generator.generate(model)
```

_Rejected Options_:

- _Direct Symbol Usage_: Passing `KSClassDeclaration` directly to the Generator. Rejected because it makes the Generator hard to test (need to mock complex KSP interfaces) and risks "Stale Reference" exceptions if symbols are held too long.

### Standard: Interface-Based Components

Key components (`Generator`, `Writer`) are defined by interfaces, with implementations injected into the `Processor`.

_Rationale_:

Allows for easy swapping of implementations for testing or different outputs.

- `MetaComponentGenerator` can be mocked to return fixed `FileSpec`s.
- `Writer` can be swapped for an in-memory writer for assertions.
- `Parser` can be mocked to return fixed Domain Models.

_Tradeoffs_:

- _Pros_: Extremely testable, modular.
- _Cons_: Dependency injection wiring required in the Entrypoint.

### Standard: Synthesized Parameter Names

To ensure robustness, Backstab synthesizes parameter names (e.g., `arg0`, `arg1`) for the generated provider functions instead of attempting to preserve the source parameter names.

**Rationale:**
*   **Robustness**: Parameter names are not always available in compiled classes (e.g., libraries without debug info). Relying on them would cause Backstab to fail for certain dependencies.
*   **Correctness**: Synthesized names guarantee finding a unique identifier for every argument, avoiding potential naming collisions if a component builder has multiple methods with identical parameter names.
*   **Trade-off**: The generated code uses generic names (`arg0`), which slightly reduces readability, but is accepted as this is generated code primarily consumed by the compiler.

## Testing Strategy

Based on the architecture above, the testing strategy is:

1.  **Unit Tests (Processor)**: Test `ProcessorImpl` using fake `Generator`, `Writer`, and `Parser`. Focus on orchestration logic.
2.  **Unit Tests (Generator)**: Test `MetaComponentGeneratorImpl` by passing in Domain Models and asserting on `FileSpec` string output (Golden Testing).
3.  **Integration Tests**: Use `BackstabTest` to compile real Components using the processor, and then *verify* the generated code structure using robust Reflection assertions. This verifies that:
    *   The generated Modules are syntactically correct (compilation success).
    *   The generated Modules contain the expected Dagger annotations (`@Module`, `@Provides`).
    *   The generated Modules provide the expected Component types with the correct dependencies (parameter types).
    *   *Note*: Direct instantiation via Dagger in the test target is currently limited by the build system's handling of mixed KSP/Kapt sources in a single target. Rigorous reflection-based verification serves as the primary correctness check, matching the signatures Dagger would expect.

> [!NOTE]
> **KSP Testing Limitation**: We rely on integration tests for the broader system because mocking KSP objects (e.g. `KSClassDeclaration`, `Resolver`) is infeasible due to the complexity of the API. Unit tests focus on the `Model -> Output` transformation where inputs can be easily constructed.

## Contributing

When modifying this system:

1.  _Do not_ couple Business Logic to KSP unless necessary for mapping.
2.  _Do not_ block the thread in `suspend` functions; use non-blocking I/O where possible.
3.  _Do_ update this document if you change a fundamental architectural pattern.
