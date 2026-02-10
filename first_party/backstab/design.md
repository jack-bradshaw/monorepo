# Backstab Architecture and Design

This document records the architectural decisions, design patterns, and tradeoffs that underpin the
design of [Backstab](/first_party/backstab). It serves as the primary reference for understanding
the system's structure and the reasoning behind it. Contributors should update it as the underlying
code evolves.

## Overview

Backstab is a dependency injection system that enhances vanilla Dagger by generating the code to
automatically integrate components. It uses [KSP](https://kotlinlang.org/docs/ksp-overview.html) and
[KotlinPoet](https://square.github.io/kotlinpoet/) for compile-time symbol processing and code
generation. The architecture defines a clean separation between the core KSP entry point and the
custom logic of the processor, with the goal of maximizing testability and maintainability. Given
the complex and delicate nature of Dagger, KSP, and Kotlin Poet, an extensive and multi-layered
suite of tests check virtually every supported scenario and edge case.

## Background

Backstab is designed for codebases where upstream libraries provide Dagger Components to downstream
consumers instead of only providing the raw objects and passing the binding task to the consumer.
This paradigm makes Dagger Components part of the public API of a library, which offers several key
benefits:

- Encapsulation: Components encapsulate binding details, internal modules, and transitive
  dependencies, keeping them as private implementation details.
- Composability: Consumers can compose Dagger graphs without deep awareness of upstream library
  internals.
- Contracts: It establishes strict architectural boundaries, treating the dependency graph as a
  series of opaque subsystems integrating via well-defined contracts.
- Simplicity: Downstream consumers never have to consider how to instantiate upstream types or how
  to bind implementations to interfaces.

In contrast, exposing raw objects without components forces the consumer to understand and manage
the library's internal structure (and the structure of its transitive dependencies). They must know
which objects to bind to interfaces, how to instantiate them, where to source transitive
dependencies, and which objects to combine into scopes. This leaks implementation details and
creates a brittle ecosystem where upstream refactors can easily break downstream consumers.

## Problem Statement

Adopting the approach described in [Background](#background) is not without disadvantages. Dagger
relies on manual component instantiation and integration, meaning engineers must construct via
builders or factories and wire them together via nested factory/builder calls. This requires
considerable boilerplate at every consumption site, for example:

```kotlin
// 1. Manually instantiate leaf nodes
val network = DaggerNetworkComponent.builder()
    .okHttpClient(client)
    .build()

val auth = DaggerAuthComponent.builder()
    .network(network) // Pass shared dependency
    .build()

// 2. Manually instantiate mid-level nodes (requiring leaves)
val session = DaggerUserSessionComponent.builder()
    .network(network) // Pass shared dependency again
    .auth(auth)       // Pass sibling dependency
    .build()

// 3. Manually wire the root (requiring all of the above)
val app = DaggerApplicationComponent.builder()
    .network(network) // Pass shared dependency A THIRD TIME
    .analytics(DaggerAnalyticsComponent.builder()
        .session(session) // Pass mid-level dependency
        .build())
    .feature(DaggerFeatureComponent.builder()
        .session(session) // Pass mid-level dependency again
        .auth(auth)       // Pass leaf dependency again
        .build())
    .build()
```

This approach has three core issues:

1.  Duplication: Every consumption point must manually define the entire component graph.
1.  Fragility: Changes to upstream components can easily break downstream consumers.
1.  Complexity: Consumers must be aware of the entire upstream component graph to use a single
    component.

These are violations of various clean coding best practices and architectural principles. The
problem is particularly egregious in tests, where deep nodes are replaced with fakes, because the
consumer must manually call every layer of the builder chain to access the deep nodes. It is neither
scalable nor ergonomic to operate this way, but the alternative of exposing raw objects for
downstream consumption is equally unviable; therefore, a third solution is required. There must be a
way to use upstream component definitions without the unscalable burden, and the answer is code
generation, ironically, with Dagger.

## Solution

Backstab automates component integration by fundamentally altering the way components are integrated
at the consumption point. Instead of manually wiring components together and managing a deeply
nested dependency graph, consumers specify the components they want, those components are merged
into a flat set, and Backstab automatically wires them together. This allows consumers to focus on
choosing and using functionality instead of wiring together components.

First, upstream components are annotated with `@Backstab`. This triggers the generation of a Dagger
module that encapsulates the component's instantiation logic (Builder, Factory, or create) and
binding requirements. It essentially allows Backstab to construct the component.

```kotlin
@Backstab
@Component
interface AuthComponent {
    fun authManager(): AuthManager

    @Component.Builder
    interface Builder {
        fun build(): AuthComponent
    }
}
```

Next, an Aggregate Component is defined to select the desired components using the installed
modules. This component acts as the root of the graph and handles the wiring automatically via the
generated Backstab modules.

```kotlin
@AggregateScope
@Component(modules = [
    NetworkComponent_AggregateModule::class,
    AuthComponent_AggregateModule::class,
    UserSessionComponent_AggregateModule::class,
    AnalyticsComponent_AggregateModule::class,
    FeatureComponent_AggregateModule::class,
    ApplicationComponent_AggregateModule::class
])
interface AggregateComponent {
    fun applicationComponent(): ApplicationComponent
}
```

Using `@Module`s to select upstream components allows alternative configurations to be easily
generated by defining a new aggregate. For example, replacing a component with a test double:

```kotlin
@AggregateScope
@Component(modules = [
    // All other modules are unchanged.
    AuthComponent_FakeAggregateModule::class, // Testing override

])
interface TestAggregateComponent {
    fun applicationComponent(): ApplicationComponent
}
```

Finally, the consumer instantiates the Aggregate Component to access the fully wired graph. The
complex wiring logic is completely hidden.

```kotlin
val app = DaggerAggregateComponent.create()
    .applicationComponent() // Fully wired instance
```

In summary, components are annotated with `@Backstab` to generate component-component integration
code, an aggregate component references the generated integration code to automate the wiring, and
consumers simply depend on the aggregate component instead of handling the wiring manually. This is
dependency injection self-referentially applied to itself.

## Architecture

The architecture uses separation of concerns, modularity and various general engineering best
practices to keep the code clean and maintainable.

### Separation of Concerns

The system is divided into an [entrypoint component](/first_party/backstab/entrypoint) and a
[core component](/first_party/backstab/processor/core), with finer subcomponents as necessary. The
entrypoint component implements the basic KSP
([SymbolProcessor](https://github.com/google/ksp/blob/main/api/src/main/kotlin/com/google/devtools/ksp/processing/SymbolProcessor.kt))
interface, but contains minimal custom logic, instead it delegates to the core component, which
contains the majority of the custom logic. This clean separation of concerns decouples the custom
logic from the underlying platform (i.e. KSP), which is critical for testability and maintainability
since the types of KSP cannot be controlled in a test without performing in-test compilation.
Overall, this approach allows the custom logic to be unit tested in isolation while integration
tests verify the entire system works as intended (further details in the
[testing strategy](#testing-strategy)).

### Intermediate Representation Model

The [first layer](/first_party/backstab/processor/parser) of the processor parses inputs from KSP
and transforms KSP types to an intermediate representation (i.e. a domain-specific model), and all
processing after that uses the intermediate representation instead of the raw KSP types. This
minimizes coupling between KSP and the custom Backstab processor, which is critical to testing since
KSP types cannot easily be constructed in tests without running KSP in the test. While running KSP
in a test is technically possible, the maintenance overhead of maintaining a secondary compiler
system alongside Bazel is undesirable. The [testing strategy](#testing-strategy) is further
documented below.

### KSP instead of KAPT

Backstab relies on KSP's multi-stage processing which is unavailable in KAPT. Only KSP supports the
following interleaved generation cycle:

1. Stage 1: Dagger finds a component annotated with `@Component` and generates the implementation.
1. Stage 2: Backstab finds a component annotated with `@Backstab` and generates a Dagger module that
   uses the Dagger-generated implementation.
1. Stage 3: Dagger detects the new module and processes it as usual.

Without the multi-stage processing of KSP, the modules would remain unprocessed and could not be
used.

Note: Dagger KAPT is not officially supported as of Feb 2026, therefore a
[workaround](/first_party/dagger) was created to support KSP.

### Limited use of KotlinPoet

To ensure long-term maintainability and minimize API bloat, the use of KotlinPoet types within the
intermediate representation model is strictly limited. Component interfaces and the domain model
should not depend on complex types from third-party libraries (like `FunSpec` or `TypeSpec`) because
such dependencies increase maintenance costs and invite misuse by exposing generator-specific logic
to the semantic layer. Instead, KotlinPoet usage is restricted to simple, value-like types such as
`ClassName`, `TypeName`, and `MemberName`. This preserves a clean, POJO-like domain model that
encapsulates semantic meaning while delegating technical code-generation details to the final
emittance stage.

## Design Decisions

The design decisions capture the tradeoffs and compromises made to arrive at the final design. They
are not as broad as the architecture but nonetheless are critical for maintainability and efficacy.

### Async-First Internal APIs

All internal interfaces (`Processor`, `Generator`, `Writer`) use `suspend` functions, even though
KSP's `process()` method is synchronous and requires a `runBlocking` call. While concurrency is not
strictly necessary right now, programming concurrently in an asynchronous environment is easier than
retrofitting asynchronicity into a synchronous environment, so this approach keeps the code future
proofed.

### Synthesized Parameter Names

Backstab uses non-descriptive synthesized parameter names in generated code (e.g. `arg0`, `arg1`)
instead of attempting to preserve the source parameter names. This ensures the system is robust by
avoiding two critical issues:

- Parameter names are not always available in compiled classes.
- Parameter names are not always unique and could collide.

Trading off readability for reliability is acceptable given the need to support the above cases.

### Aggregate Components as Leaves

Custom scoping is deliberately unsupported. All generated Backstab modules use the standard
`@AggregateScope` which ensures all modules are mutually compatible and can be combined into any
aggregate component without conflicts. This forces every Aggregate Component to be a terminal "leaf"
node in the process lifecycle, rather than a node in a broader dependency graph. While this prevents
sharing state implicitly between aggregate components, it is critical for the model of aggregate
components being containers to manage other components, not first class components that can be
depended on. This also ensures that the graph is always valid and predictable.

## Testing Strategy

Backstab employs a "Golden File" and "Integration" testing strategy to ensure both code generation
fidelity and end-to-end graph validity.

### Dual Strategy

Backstab uses a dual strategy to ensure reliability.

1. Unit tests check the generated code but do not involve compilation. A series of pre-defined
   inputs (defined using the intermediate representation) drive the code generator, and the
   resulting generated code is compared against goldens (must match byte for byte). They ensure the
   generated code is inspected and conforms to general coding standards instead of simply being a
   black box.
1. Integration tests check the E2E functionality of the system from source through compilation. A
   series of predefined inputs (defined as Dagger Components) are compiled, and the resulting
   aggregate components are exercised to ensure they function correctly. They ensure the entire
   system works across a wide array of conditions.

Both approaches are necessary and complement each other. Unit tests alone would only verify the
generated code (not the emergent functionality) and integration tests alone would only verify the
emergent functionality (leaving the generated code as a potentially unruly black box). With both
tests, the internals and the overall system remain stable and effective.

### Unit Test Coverage

Unit tests verify the generator's behavior across three primary axes:

1. Instantiator Parity: Ensures correct logic generation for all valid Dagger instantiators
   (Builders, Factories, and implicit creation).
2. Qualifier Fidelity: Verifies that all qualifier annotations (`@Named` and `@Qualifier`) are
   correctly propagated from source to generated code.
3. Binding Topology: Confirms that the generator correctly delegates bindings to the underlying
   component methods.

Various minor edge cases are also checked.

### Integration Test Coverage

Integration tests validate the system against three major dimensions:

1. Topology Dimensions: Verifies behavior across varying graph shapes, including Isolated, Shallow,
   Wide, Deep, Broad (Wide + Deep), and Diamond.
2. Type System Edge Cases: Ensures correct handling of complex Kotlin/Java interoperability
   scenarios such as Generics, TypeAliases, and Multibindings.
3. Naming Robustness: Simulates hostile naming environments to guarantee that generated symbols do
   not collide with user-defined types.

Various minor edge cases are also checked.

### Integration Test Structure

The integration tests require sources for compilation (by Bazel with the Backstab plugin enabled).
The sources are grouped by test case (using interfaces) to prevent fragile cross-test dependencies.
While this creates some duplication, as there are common elements which could be shared between test
cases, it prioritizes isolation over code reuse. This approach ensures a change to the inputs of one
test case cannot inadvertently break another.

## Rejected Alternatives

The following alternatives were considered for the overall system architecture but were rejected for
the reasons listed below.

- Global Singleton Scope: Flattening the entire graph into a single `javax.inject.Singleton` scope
  was rejected because it prevents the use of isolated subgraphs or custom scopes (such as
  `RequestScope` or `SessionScope`), which are critical for resource management and leak prevention.
- Module Merging (Anvil or Hilt-style): The framework could automatically merge all modules on the
  classpath. However, this dissolves architectural boundaries between libraries, as all bindings
  become effectively public and available to the entire integration graph. Explicit component
  encapsulation allows library authors to strictly define their public API surface (Facade pattern),
  hiding internal implementation details and preserving modularity.
- Exposing Modules: Upstream components could expose Dagger Modules instead of Dagger Components,
  but since modules must bind into a scope (or be unscoped), they would need to be aware of the
  scope used by the downstream consumer. This requires either foreknowledge of the consumer
  (infeasible) or a pre-defined scope (inflexible). Exposing components instead of modules allows
  the consumer to provide bindings that can be used by downstream consumers without awareness of the
  downstream consumer.
