# Dagger Directives

Directives for [Dagger](https://dagger.dev) in this repository.

## Contents

This document is extensive, and while each directive is simple, the broader architecture they
promote may be unclear; therefore, an [end-to-end example](#end-to-end-example) is provided to aid
comprehension, and the underlying architectural [rationale](#rationale) is provided to link the
individual directives to broader engineering principles. The directives are presented first for ease
of reference; however, first time readers may find the rationale and example more enlightening.

## Terminology

The following definitions apply throughout this document:

- Naked interface: An interface without Dagger annotations that defines provisioning functions
  (effectively a Dagger component without the @Component annotation).
- Component-annotated interface: An interface annotated with `@Component` that extends a naked
  interface.
- Dagger-generated component: The concrete component implementation generated when Dagger finds a
  Component-annotated interface.
- Module: A class or interface annotated with
  [`@Module`](https://dagger.dev/api/latest/dagger/Module.html).
- Scope: A custom annotation meta-annotated with
  [`@Scope`](https://dagger.dev/api/latest/dagger/Scope.html).
- Production Component: A component that is intended for non-test code.
- Testing Component: A component provided by a library to offer test doubles for its production
  components (e.g. `TestFooComponent` providing `FakeFoo`).
- Private Test Component: A component that exists for a single test or a related set of tests.

The distinction between testing components and private test components is subtle but critical. The
former are provided by libraries for use in arbitrary tests; whereas, the latter exist entirely to
service a specific set of tests, either as subjects or supporting systems. The difference is the
coupling between the component and the test. When they are loosely coupled (component is provided
independent of any specific test) it is a testing component; whereas, when they are highly coupled
(component exists only for the specific test) it is a private test component.

## Scope

All non-Hilt Dagger components and modules in this repository must conform to these directives;
however, the contents of [third_party](/third_party) are explicitly exempt, as they originate from
external sources.

## Component Definition

Directives for how to define a single component correctly, focusing on structure, naming, and
mechanical constraints.

### Standard: Naked Interfaces

Components must be defined as naked interfaces, then extended by Component-annotated interfaces to
trigger Dagger-generated component creation.

Example:

```kotlin
// Naked interface
interface FooComponent {
  fun foo(): Foo
}

// Component-annotated interface
@Component(modules = [FooModule::class])
interface FooComponentImpl : FooComponent
```

Dagger components are interfaces; however, the presence of a `@Component` annotation implicitly
creates a Dagger-generated implementation in the same artifact; therefore, depending on a
Component-annotated interface implicitly forces downstream consumers to include classes they may not
need; consequently, separating naked interfaces from Component-annotated interfaces allows consumers
to depend on the API (the naked interface) without including the implementation (the generated
code).

Exemption: Private test components may be defined directly as Component-annotated interfaces without
a separate naked interface.

### Standard: Tests Extend Production

Testing components must be defined as naked interfaces that extend the naked interface of a
Production Component, then extended by Component-annotated interfaces to trigger Dagger-generated
component creation.

Example:

```kotlin
// Production Component naked interface
interface FooComponent {
  fun foo(): Foo
}

// Testing Component naked interface
interface TestFooComponent : FooComponent {
  fun testScope(): TestScope
  fun launcher(): Launcher
}

// Test Component-annotated interface
@FooScope
@Component(modules = [TestFooModule::class])
interface TestFooComponentImpl : TestFooComponent
```

This allows testing components to be used interchangeably wherever production components are
expected, while permitting the improved introspection and alternate functionality required for
testing.

### Practice: Additional Testing Component Bindings

Testing components commonly export additional bindings.

Example:

```kotlin
interface TestFooComponent : FooComponent {
  fun testHelper(): TestHelper
}
```

This allows tests to consume test-specific bindings without changing the public production API.

### Practice: Component Naming

Component names should follow three conventions:

- Naked interfaces should have a `Component` suffix.
- Component-annotated interfaces should have an `Impl` suffix.
- Naked interfaces for testing components match the production interface name with a `Test` prefix.

Example:

```kotlin
/** Production Component naked interface. */
interface FooComponent { }

/** Production Component Component-annotated interface. */
interface FooComponentImpl : FooComponent { }

/** Testing Component naked interface. */
interface TestFooComponent : FooComponent { }

/** Testing Component Component-annotated interface. */
interface TestFooComponentImpl : TestFooComponent { }
```

These conventions clearly distinguish naked interfaces from component-annotated interfaces, and the
`Test` prefix signals testing component variants; furthermore, like all naming conventions, this
reduces mental overhead and aids discovery across the codebase.

Exemption: Private test components may omit the `Impl` suffix (e.g. `TestComponent` instead of
`TestComponentImpl`).

### Standard: Scope Naming Convention

The name of the custom scope associated with a component must inherit the name of the root naked
interface, minus "Component", with "Scope" appended.

Example:

```kotlin
/** Custom Scope */
@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class FooScope

/** Production Component Naked Interface */
interface FooComponent { }

/** Production Component Component-annotated Interface */
@FooScope
@Component
interface FooComponentImpl : FooComponent { }

/** Testing Component Naked Interface */
interface TestFooComponent : FooComponent { }

/** Testing Component Component-annotated Interface */
@FooScope
@Component
interface TestFooComponentImpl : TestFooComponent { }
```

This associates a scope with its component, thereby preventing conflicts; furthermore, like all
naming conventions, this reduces mental overhead and aids discovery across the codebase.

### Standard: Builder Naming

Component builders must be called `Builder`.

Example:

```kotlin
@Component
interface FooComponentImpl : FooComponent {
  @Component.Builder
  interface Builder {
    @BindsInstance fun binding(bar: Bar): Builder
    fun build(): FooComponentImpl
  }
}
```

This allows engineers to predict the API surface of any component; furthermore, like all naming
conventions, this reduces mental overhead and aids discovery across the codebase.

Exception: Private test components may be named arbitrarily, and are exempt from these naming
standards. Often "TestComponent" is sufficient for readability and maintainability.

### Standard: Binding Function Naming

Component builder functions that bind instances must be called `binding`; however, when bindings use
qualifiers, the qualifier must be appended.

Example:

```kotlin
@Component
interface ConcurrencyComponent {
  @Component.Builder
  interface Builder {
    // Unqualified
    @BindsInstance fun binding(bar: Bar): Builder

    // Qualified
    @BindsInstance fun bindingIo(@Io scope: CoroutineScope): Builder
    @BindsInstance fun bindingMain(@Main scope: CoroutineScope): Builder

    fun build(): ConcurrencyComponent
  }
}
```

This clarifies the mechanism of injection (instance binding vs component dependency) and prevents
collisions when binding multiple instances of the same type; furthermore, like all naming
conventions, this reduces mental overhead and aids discovery across the codebase.

### Standard: Component Dependency Function Naming

Component builder functions that set component dependencies must be called `consuming`.

Example:

```kotlin
@Component(dependencies = [Bar::class])
interface FooComponentImpl : FooComponent {
  @Component.Builder
  interface Builder {
    fun consuming(bar: Bar): Builder
    fun build(): FooComponentImpl
  }
}
```

This separates structural dependencies (consuming) from runtime data (binding), thereby making the
component's initialization logic self-documenting; furthermore, like all naming conventions, this
reduces mental overhead and aids discovery across the codebase.

### Standard: Provisioning Function Naming

Component provision functions must be named after the type they provide (in camelCase). However,
when bindings use qualifiers, the qualifier must be prepended to the function name.

Example:

```kotlin
@Component
interface FooComponentImpl : FooComponent {
  // Unqualified
  fun bar(): Bar

  // Qualified
  @Io fun ioFoo(): Foo
  @Main fun mainFoo(): Foo
}
```

This enforces consistent naming across provision functions, immediately signaling what type is being
provided and whether a qualifier is involved; furthermore, like all naming conventions, this reduces
mental overhead and aids discovery across the codebase.

### Standard: Injection Function Naming

Component injection functions must be named "inject".

Example:

```kotlin
@Component
interface FooComponentImpl : FooComponent {
  fun inject(bar: Bar)
}
```

This standardizes the injection entry point; furthermore, like all naming conventions, this reduces
mental overhead and aids discovery across the codebase.

## Architectural Policy

Directives for strategic decisions about granularity, lifecycle, coupling, and graph topology.

### Guideline: Balanced Component Scope

Component consumers should be able to use components without providing irrelevant configurations and
dependencies, in accordance with the interface segregation principle; however, components are
boilerplate-heavy, and maintaining a separate component for every interface/class is a significant
burden; therefore, contributors should balance the scope of components with their cost, and choose
boundaries that create logical groupings without excessive burden on the consumer; effectively,
weighing the cost to the maintainer against the cost to the consumer.

Too Granular: Defining a separate component for every single class (e.g. `UserCacheComponent`,
`UserFetcherComponent`, `UserParserComponent`), as this forces consumers to manually wire together
an excessive number of tiny components for every feature.

Too Broad: A single monolithic `AppComponent` that provides every binding in the application,
forcing every consumer (especially tests) to depend on (and potentially mock) the entire world, even
for simple tasks.

Just Right: A `UserComponent` that groups related functionality (Cache, Fetcher, Parser) and exposes
the important high-level APIs, while keeping internal details hidden. This allows consumers to
depend on the "User" subsystem as a single logical unit.

Striking a balance between granularity (adhering to ISP) and convenience (reducing boilerplate)
ensures components remain usable and maintainable without imposing excessive burden on either the
maintainer or consumer.

### Practice: Library-Provided Components

Libraries should provide components that expose their functionality and declare their component
dependencies instead of only providing raw classes/interfaces.

Positive Example: A `Networking` library provides a `NetworkingComponent` that exposes an
`OkHttpClient` binding and depends on a `CoroutinesComponent`.

Negative Example: A `Networking` library that provides various interfaces and classes, but no
component, thereby requiring downstream consumers to define modules and components to wire them
together.

This allows consumers to use the library by simply declaring a dependency, rather than needing to
understand its internal structure or mechanically wire together its classes.

### Standard: Depend on Naked Interfaces

Component dependencies must reference naked interfaces, not Component-annotated interfaces or
Dagger-generated components.

Positive example:

```kotlin
@Component(dependencies = [FooComponent::class])
interface BarComponent { }
```

Negative example:

```kotlin
@Component(dependencies = [FooComponentImpl::class])
interface BarComponent { }
```

Negative example:

```kotlin
@Component(dependencies = [DaggerFooComponentImpl::class])
interface BarComponent { }
```

This maintains a clean separation between interfaces and implementations, thereby allowing alternate
implementations to be switched out (e.g. in tests, and as the system evolves), all while optimizing
build times. It follows from the SOLID principles (specifically, dependency inversion), which are
the foundations of effective object-oriented programming.

### Practice: Component Build Separation

Naked interfaces and Component-annotated interfaces should be defined in separate files, each with
their own build target.

Example files:

```text
coroutines/
├── CoroutinesComponent.kt      # Naked interface
├── CoroutinesComponentImpl.kt  # Production Component
└── ...
```

Example BUILD structure:

```starlark
kt_jvm_library(
    name = "scope",
    srcs = ["CoroutinesScope.kt"],
    visibility = ["//visibility:public"],
    deps = ["//:dagger"],
)

kt_jvm_library(
    name = "component",
    srcs = ["CoroutinesComponent.kt"],
    visibility = ["//visibility:public"],
    deps = [
        ":scope",
        # Minimal dependencies - no Dagger
    ],
)

kt_jvm_library(
    name = "component_impl",
    srcs = ["CoroutinesComponentImpl.kt"],
    visibility = ["//first_party:__subpackages__"],
    deps = [
        ":component",
        ":scope",
        "//:dagger",
        # Other production dependencies
    ],
)
```

This is an extension of the granular target requirements in
[bazel.md](/first_party/contributing/tooling/bazel.md). It creates classpath separation at the build
system level, which allows consumers to depend on the naked interface without including the
Dagger-generated code in the classpath, thereby improving build performance and supporting the
dependency-inversion principle promoted by the naked interfaces directive.

Exemption: Private test components may be defined in the test file, and thus, implicitly included in
the test's build target. This is acceptable because tests are usually terminal nodes in the build
graph (i.e. they have no dependents).

### Standard: Custom Scope Required

Components must be bound to a custom Dagger scope.

Example:

```kotlin
@FooScope
@Component
interface FooComponentImpl : FooComponent {
  fun foo(): Foo
}
```

Unscoped bindings can lead to subtle bugs where expensive objects are recreated or shared state is
lost; whereas, explicit lifecycle management ensures objects are retained only as long as needed,
preventing these issues; furthermore, the granular component architecture promoted by this document
relies on strict scoping to manage efficient resource usage.

Exemption: Private test components may be unscoped since they are usually not shared or reused.

### Standard: Module Inclusion Restrictions

Components must only include modules defined within their own package or its subpackages; however,
they must never include modules from a subpackage if another component is defined in an intervening
package.

Example:

Given the following package structure:

```text
src
├── a
│   ├── AComponent
│   ├── AModule
│   ├── sub1
│   │   └── Sub1Module
│   └── sub2
│       ├── Sub2Component
│       └── sub3
│           └── Sub3Module
└── b
    └── BModule
```

`AComponent` may include `AModule` (same package) and `Sub1Module` (subpackage with no intervening
component), but not `Sub3Module` (intervening `Sub2Component` in `a.sub2`) or `BModule` (not a
subpackage of `a`).

This enforces strict architectural layering and prevents dependency cycles (spaghetti code), thereby
ensuring proper component boundaries and maintainability.

### Practice: Dependencies Over Subcomponents

Component dependencies should be used instead of subcomponents.

Example: `Foo` depends on `Bar` via `@Component(dependencies = [Bar::class])` rather than using
`@Subcomponent`.

While subcomponents are a standard feature of Dagger, prohibiting them favors a flat
composition-based component graph, thereby reducing cognitive load, allowing components to be tested
in isolation, and creating a more scalable architecture.

### Practice: Cross-Package Dependencies

Components may depend on components from any package.

Example: `Foo` in `a.b` can depend on `Bar` in `x.y.z`.

Allowing components to depend on each other regardless of location promotes reuse and avoids
excessive repository hierarchy.

### Practice: Component Target Naming

Component BUILD targets should be named `component` for the interface and `component_impl` for the
production implementation, but there is no need to include `test` in the name anywhere, as this is
usually implied by the package path.

Example: `//first_party/coroutines:component` and `//first_party/coroutines:component_impl`.

This communicates the purpose of each target while preventing naming collisions with general
targets; furthermore, like all naming conventions, this reduces mental overhead and aids discovery
across the codebase.

### Practice: Module Target Naming

Module BUILD targets should be named `module`. When multiple modules exist, they may use any naming
scheme desired to differentiate the targets so long as they begin or end with `module`.

Example: `//first_party/coroutines:module` or `//first_party/coroutines:io_module`.

This communicates the purpose of each target while preventing naming collisions with general
targets; furthermore, like all naming conventions, this reduces mental overhead and aids discovery
across the codebase.

## Factory Functions

Requirements for the factory functions that instantiate components for ease of use.

### Standard: Factory Function Required

Components must have an associated factory function that instantiates the component.

Example:

```kotlin
@Component(dependencies = [QuuxComponent::class])
interface FooComponentImpl : FooComponent {
  // ...
}

fun fooComponent(quux: QuuxComponent = DaggerQuuxComponentImpl.create(), qux: Qux): FooComponent =
  DaggerFooComponentImpl.builder()
    .consuming(quux)
    .binding(qux)
    .build()
```

This integrates cleanly with Kotlin, thereby significantly reducing the amount of manual typing
required to instantiate components.

Exemption: Private test components may exclude the factory function.

### Standard: Default Component Dependencies

Factory functions must supply default arguments for parameters that represent component
dependencies.

Example: `fun fooComponent(quux: QuuxComponent = DaggerQuuxComponentImpl.create(), ...)`

Providing defaults for dependencies allows consumers to focus on the parameters that actually vary,
thereby improving developer experience and reducing boilerplate.

### Practice: Production Defaults

The default arguments for component dependency parameters in factory functions should be production
components, even when the component being assembled is a testing component.

Example: `fun testFooComponent(quux: QuuxComponent = DaggerQuuxComponentImpl.create(), ...)`

This ensures tests exercise real production components and behaviors as much as possible, thereby
reducing the risk of configuration drift between test and production environments.

### Practice: Factory Function Location

Factory functions should be defined as top-level functions in the same file as the component.

Example: `fooComponent()` function in same file as `FooComponent` interface.

Co-locating the factory with the component improves discoverability.

### Practice: Factory Function Naming

Factory function names should match the naked interface, but in lower camel case.

Example: `FooComponent` is associated with `FooComponentImpl` which is instantiated by the
`fun fooComponent(...)` factory function.

This matches the factory to the component; furthermore, like all naming conventions, this reduces
mental overhead and aids discovery across the codebase.

### Standard: Factory Function Return Types

Factory functions must return the naked interface type rather than the Component-annotated interface
type.

Example:

```kotlin
fun fooComponent(...): FooComponent = DaggerFooComponentImpl.create()

fun testFooComponent(...): TestFooComponent = DaggerTestFooComponentImpl.create()
```

This ensures callers interact only with the abstraction, hiding the `Dagger...Impl` detail from the
call site.

### Practice: Default Non-Component Parameters

Factory functions should supply default arguments for parameters that do not represent component
dependencies (where possible).

Example: `fun fooComponent(config: Config = Config.DEFAULT, ...)`

Sensible defaults allow consumers to only specify non-standard configuration when necessary, thereby
reducing cognitive load.

## Modules and Build Targets

Directives regarding Dagger modules and their placement in build targets.

### Standard: Separate Module Targets

Modules must be defined in separate build targets to the objects they provide/bind.

Example: `BarModule` in separate build target from `Baz` implementation.

Separating implementation from interface/binding prevents changing an implementation from
invalidating the cache of every consumer of the interface, thereby improving build performance.
Additionally, it ensures consumers can depend on individual elements independently (crucial for
Hilt) and allows granular binding overrides in tests.

Exemption: Modules defined exclusively for private test components may be co-located in the test
target/file.

### Standard: Dependency Interfaces

Module function parameters must use abstractions rather than implementations.

Example: `BarModule` function accepts `Baz` interface, not `BazImpl`.

This decouples the module and its bindings from concrete implementations, thereby preventing
circular dependencies, allowing implementations to be swapped, and supporting dependency inversion.

## Rationale

The directives in this document are not arbitrary. They were meticulously crafted to make the most
of Dagger, as it remains a powerful tool in any JVM application, while steering contributors away
from the more unsustainable and unmaintainable patterns it can produce, and offering an approach
that scales well as the codebase and organization grows in complexity. The justification for their
design can be explained in terms of the architecture they promote, the engineering principles they
satisfy, and the build-system improvements they offer.

### Architecture

The directives promote a pattern across the repository where libraries and other such upstream
components expose fully-formed dagger components to downstream consumers, thereby allowing consumers
to use upstream functionality without deep knowledge of the underlying system, specifically which
classes to bind to which interfaces, and how components fit together. A consumer can simply find a
component from a library, depend on it in their own component, supply the dependencies the upstream
component explicitly requires, and use the provided functionality. Contrast this with the stock
dagger approach, where components are exclusively defined by downstream consumers, thereby requiring
consumers to have deep awareness of the bindings and dependencies of upstream libraries. In the
architecture this document promotes, such implicit awareness is unnecessary, and the API contract of
each component documents any transitive dependencies.

Beyond improved discoverability, the architecture also promotes granular replacement of components
for testability and flexibility. By leaning heavily into interface-based programming, and defining
components as interfaces with dagger-generated implementations, it allows different functionality to
be switched out without modifying the overall system architecture. This ensures tests can switch out
critical pieces for observability and control, while still exercising the real system as much as
possible. This is a foundational piece of testable architectures and is critical to ensuring tests
prevent real failures (as opposed to being so artificial that they catch no real bugs).

### Engineering Principles

The directives are effectively expressions of the following foundational engineering principles:

- Interface Segregation Principle (ISP): Downstream consumers should be able to depend on the
  minimal API required for their task without being forced to consume/configure irrelevant objects.
  This reduces cognitive overhead for both maintainers and consumers, lowers computational costs at
  build time and runtime, and reduces the scope of rework when APIs change. It's supported by
  directives such as the "Narrow Scoped Components" practice, which calls for small granular
  components instead of large God Objects, and the "Dependencies Over Subcomponents" practice, which
  encourages composition over inheritance.
- Dependency Inversion Principle: High-level elements should not depend on low-level elements;
  instead, both should depend on abstractions. This reduces the complexity and scope of changes by
  allowing components to evolve independently and preventing unnecessary recompilation (in a complex
  build system such as Bazel). It's supported by the "Naked Interfaces" directive, which requires
  the use of interfaces rather than implementations, and the "Module Inclusion Restrictions"
  standard, which enforces strict architectural layering.
- Abstraction and API Usability: Complex subsystems should expose simple, predictable interfaces
  that hide their internal complexity and configuration requirements. This allows maintainers and
  consumers to use and integrate components without deep understanding of the implementation. It's
  supported by the "Factory Function Required" standard, which encourages simple entry points, and
  "Default Component Dependencies", which provides sensible defaults to eliminate "Builder Hell".
- Liskov Substitution Principle (LSP): Objects of a superclass must be replaceable with objects of
  its subclasses without breaking the application. This ensures test doubles can be seamlessly
  swapped in during tests, thereby improving testability without requiring changes to production
  code, and ensuring as much production code is tested as possible. It's supported by the "Testing
  Component Extension" standard, which mandates that testing components inherit from production
  component interfaces.
- Compile-Time Safety (Poka-Yoke): The system is designed to prevent misconfiguration errors (i.e.
  "error-proofing"). By explicitly declaring component dependencies in the component interface,
  Dagger enforces their presence at compile time, and fails if they are missing. This gives
  consumers a clear, immediate signal of exactly what is missing or misconfigured, rather than
  failing obscurely at runtime. It's supported by the "Library-Provided Components" practice, which
  mandates fully declared dependencies, and the "Factory Function Required" standard, which
  mechanically ensures all requirements are satisfied effectively.

Overall, this architecture distributes complexity across the codebase and promotes high cohesion by
defining components nearest to the objects they expose, serving as both the public API and the
integration layer for utilities. The disadvantages of this approach and a strategy for mitigation
are discussed in the [future work](#future-work) appendix.

### Build Optimization

The directives promote a repository structure which is well-suited to build optimization in two
ways:

1. Widespread dependency on interfaces instead of implementations reduces the scope of downstream
   rebuilds when upstream components change.
1. Segregating the build system into granular targets improves cache performance and minimizes the
   need to rebuild unchanged code.

Such optimizations are critical in monorepos at scale.

## End to End Example

The following example demonstrates a complete Dagger setup and usage that adheres to all the
directives in this document. It features upstream (User) and downstream (Profile) components,
separate modules for production and testing (including fake implementations), and strict separation
of interface and implementation via naked interfaces.

### User Feature

Common elements:

```kotlin
/** Custom Scope */
@Scope @Retention(AnnotationRetention.RUNTIME) annotation class UserScope

/** Domain Interface */
interface User

/** Production Component Naked Interface */
interface UserComponent {
  fun user(): User
}
```

Production elements:

```kotlin
/** Production Component Implementation Class */
@UserScope class RealUser @Inject constructor() : User

/** Production Module */
@Module
interface UserModule {
  @Binds fun bind(impl: RealUser): User

  companion object {
    @Provides fun provideTimeout() = 5000L
  }
}

/** Production Component Component-annotated Interface */
@UserScope
@Component(modules = [UserModule::class])
interface UserComponentImpl : UserComponent {
  @Component.Builder
  interface Builder {
    fun build(): UserComponentImpl
  }
}

/** Production Factory Function */
fun userComponent(): UserComponent = DaggerUserComponentImpl.builder().build()
```

Test elements:

```kotlin
/** Fake Implementation */
@UserScope class FakeUser @Inject constructor() : User

/** Fake Module */
@Module
interface FakeUserModule {
  @Binds fun bind(impl: FakeUser): User
}

/** Testing Component Naked Interface */
interface TestUserComponent : UserComponent {
  fun fakeUser(): FakeUser
}

/** Testing Component Component-annotated Interface */
@UserScope
@Component(modules = [FakeUserModule::class])
interface TestUserComponentImpl : TestUserComponent {
  @Component.Builder
  interface Builder {
    fun build(): TestUserComponentImpl
  }
}

/** Testing Component Factory Function */
fun testUserComponent(): TestUserComponent = DaggerTestUserComponentImpl.builder().build()
```

### Profile Feature

Common elements:

```kotlin
/** Custom Scope */
@Scope @Retention(AnnotationRetention.RUNTIME) annotation class ProfileScope

/** Domain Interface */
interface Profile

/** Production Component Naked Interface */
interface ProfileComponent {
  fun profile(): Profile
}
```

Production elements:

```kotlin
/** Real Implementation */
@ProfileScope class RealProfile @Inject constructor(
  val user: User,
  private val id: ProfileId
) : Profile {
  data class ProfileId(val id: String)
}

/** Production Module */
@Module
interface ProfileModule {
  @Binds fun bind(impl: RealProfile): Profile
}

/** Production Component @Component-annotated Interface */
@ProfileScope
@Component(dependencies = [UserComponent::class], modules = [ProfileModule::class])
interface ProfileComponentImpl : ProfileComponent {
  @Component.Builder
  interface Builder {
    fun consuming(user: UserComponent): Builder
    @BindsInstance fun binding(id: ProfileId): Builder
    fun build(): ProfileComponentImpl
  }
}

/** Production Factory Function */
fun profileComponent(
  user: UserComponent = userComponent(),
  id: ProfileId = ProfileId("prod-id")
): ProfileComponent =
  DaggerProfileComponentImpl.builder()
    .consuming(user)
    .binding(id)
    .build()
```

Test elements:

```kotlin
/** Testing Component Naked Interface */
interface TestProfileComponent : ProfileComponent

/** Testing Component @Component-annotated Interface */
@ProfileScope
@Component(dependencies = [UserComponent::class], modules = [ProfileModule::class])
interface TestProfileComponentImpl : TestProfileComponent {
  @Component.Builder
  interface Builder {
    fun consuming(user: UserComponent): Builder
    @BindsInstance fun binding(id: ProfileId): Builder
    fun build(): TestProfileComponentImpl
  }
}

/** Testing Component Factory Function */
fun testProfileComponent(
  user: UserComponent = userComponent(),
  id: ProfileId = ProfileId("test-id")
): TestProfileComponent =
  DaggerTestProfileComponentImpl.builder()
    .consuming(user)
    .binding(id)
    .build()
```

### Usage

Example of production component used in production application:

```kotlin
class Application {
  fun main() {
    // Automatically uses production implementations (RealUser, RealProfile)
    val profile = profileComponent().profile()
    // ...
  }
}
```

Example of production profile component used with test user component in a test:

```kotlin
@Test
fun testProfileWithFakeUser() {
  // Setup: Create the upstream testing component (provides FakeUser)
  val fakeUserComponent = testUserComponent()
  val fakeUser = fakeUserComponent.fakeUser()

  // Act: Inject it into the downstream component
  val prodProfileComponent = profileComponent(user = fakeUserComponent)
  val profile = prodProfileComponent.profile()

  // Assert: Verify integration
  assertThat(profile.user).isEqualTo(fakeUser)
}
```

## Future Work

The main disadvantage of the pattern this document encodes is the need for a final downstream
assembly of components, which can become boilerplate heavy in deep graphs. For example:

```kotlin
fun main() {
  // Level 1: Base component
  val core = coreComponent()

  // Level 2: Depends on Core
  val auth = authComponent(core = core)
  val data = dataComponent(core = core)

  // Level 3: Depends on Auth, Data, AND Core
  val feature = featureComponent(
    auth = auth,
    data = data,
    core = core
  )

  // Level 4: Depends on Feature, Auth, AND Core
  val app = appComponent(
    feature = feature,
    auth = auth,
    core = core
  )
}
```

A tool to reduce this boilerplate has been designed, and implementation is tracked by
[issue 264](https://github.com/jackbradshaw/monorepo/issues/264).
