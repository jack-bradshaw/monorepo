# Dagger Directives

Directives for Dagger in this repository.

This document is extensive, and while the individual directives are simple, how they all fit
together is non-trivial; therefore, an [end-to-end example](#end-to-end-example) is provided to aid
comprehension.

## Terminology

The following definitions apply throughout this document:

- Component: An interface annotated with
  [`@Component`](https://dagger.dev/api/latest/dagger/Component.html).
- Module: A class or interface annotated with
  [`@Module`](https://dagger.dev/api/latest/dagger/Module.html).
- Scope: A custom annotation meta-annotated with
  [`@Scope`](https://dagger.dev/api/latest/dagger/Scope.html).

## Scope

All Dagger components and modules in this repository must conform to these directives; however, the
contents of [third_party](/third_party) are explicitly exempt, as they originate from external
sources.

## Rationale

The directives in this document work together to promote an architectural pattern for Dagger that
follows foundational engineering best practices and principles, which in turn supports sustainable
development and improves the contributor experience. The core principles are:

- Interface Segregation Principle (ISP): Downstream consumers should be able to depend on the
  minimal API required for their task without being forced to consume/configure irrelevant objects.
  This reduces cognitive overhead for both maintainers and consumers, and lowers computational costs
  at build time and runtime. It's supported by directives such as the "Narrow Scoped Components"
  practice, which calls for small granular components instead of large God Objects, and the
  "Dependencies Over Subcomponents" practice, which encourages composition over inheritance.
- Dependency Inversion Principle: High-level elements should not depend on low-level elements;
  instead, both should depend on abstractions. This isolates implementation details in separate
  build targets, ensuring that changes in one target only trigger the linking phase for consumers,
  rather than a full recompilation. It's supported by the "Naked Component Interfaces" directive,
  which forces dependencies on interfaces rather than implementations, and the "Module Inclusion
  Restrictions" standard, which enforces strict architectural layering.
- Abstraction and API Usability: Complex subsystems should expose simple, predictable interfaces
  that hide their internal complexity and configuration requirements. This allows maintainers and
  consumers to instantiate and integrate components without deep understanding of the
  implementation. It's supported by the "Factory Function Required" standard, which provides a
  simple entry point, and "Default Component Dependencies", which provides sensible defaults to
  eliminate "Builder Hell".
- Liskov Substitution Principle (LSP): Objects of a superclass must be replaceable with objects of
  its subclasses without breaking the application. This ensures test doubles can be seamlessly
  swapped in during tests, thereby improving testability without requiring changes to production
  code, and ensuring as much production code is tested as possible. It's supported by the "Test
  Component Extension" standard, which mandates that test components inherit from production
  component interfaces.
- Compile-Time Safety (Poka-Yoke): The system is designed to prevent misconfiguration errors
  ("error-proofing"). By explicitly declaring component dependencies in the component interface,
  Dagger enforces their presence at compile time, and fails if they are missing. This gives
  consumers a clear, immediate signal of exactly what is missing or misconfigured, rather than
  failing obscurely at runtime. It's supported by the "Library-Provided Components" practice, which
  mandates fully declared dependencies, and the "Factory Function Required" standard, which
  mechanically ensures all requirements are satisfied effectively.

Overall, this architecture encourages and supports granular, maintainable components that can be
evolved independently and composed together into complex structures. Components serve as both the
public API for utilities, the integration system that ties elements together within utilities, and
the composition system that combines utilities together. For upstream utility maintainers, this
reduces boilerplate and reduces the risk of errors; for downstream utility consumers, this creates
an unambiguous and self-documenting API that can be integrated without knowledge of implementation
details; and for everyone it distributes complexity across the codebase and promotes high cohesion
(i.e. components defined nearest to the objects they expose). All together, this fosters sustainable
development by reducing cognitive and computational load.

The disadvantages of this approach are discussed in the [future work](todo link) appendix.

## Component Structure and Scoping

Rules for how components are defined, scoped, and related to one another.

### Practice: Library-Provided Components

Libraries and generic utilities should provide components that expose their functionality and
declare their component dependencies instead of only providing raw classes/interfaces.

Positive Example: A `Networking` library provides a `NetworkingComponent` that exposes an
`OkHttpClient` binding and depends on a `CoroutinesComponent`.

Negative Example: A `Networking` library that provides various interfaces and classes, but no
component, and requires downstream consumers to define modules and components to wire them together.

This approach transforms Dagger components from details of the downstream application into details
of upstream libraries. Instead of forcing consumers to understand a library's internal structure
(and figure out how to instantiate objects), library authors provide a complete, ready-to-use
components that can be composed together and used to instantiate objects. This approach is analogous
to plugging in a finished appliance instead of assembling a kit of parts: consumers just declare a
dependency on the component (e.g. a fridge), supply the upstream components (e.g. electricity), and
get the fully configured objects they need without ever seeing the wiring (e.g. cold drinks). This
approach scales well, at the cost of more boilerplate.

### Practice: Narrow Scoped Components

Components should export a minimal set of bindings, accept only the dependencies they require to
operate (i.e. with `@BindsInstance`), and depend only on the components they require to operate.

Positive Example: A `Feature` component that depends only on `Network` and `Database` components,
exposes only its public API (e.g. `FeatureUi`), and keeps its internal bindings hidden.

Negative Example: A `Feature` component that depends on a monolithic `App` component (which itself
goes against the practice), exposes various bindings that could exist in isolation (e.g.
`FeatureUi`, `Clock`, `NetworkPorts` and `RpcBridge`, `IntegerUtil`), and exposes its internal
bindings.

This allows consumers to compose functionality with granular precision, reduces unnecessary
configuration (i.e. passing instances/dependencies that are not used at runtime), and optimizes
build times. This approach is consistent with the core tenets of the Interface Segregation Principle
in that it ensures that downstream components can depend on the components they need, without being
forced to depend on unnecessary components.

### Practice: Naked Component Interfaces

Components should be defined as plain interfaces ("naked interfaces") without Dagger annotations,
and then extended by annotated interfaces for production, testing, and other purposes. Downstream
components should target the naked interfaces in their component dependencies instead of the
annotated interfaces.

Example:

```kotlin
// Definition
interface FooComponent

// Production Implementation
@Component(modules = [FooModule::class])
interface ProdFooComponent : FooComponent {
  fun foo(): Foo
}

// Testing Implementation
@Component(modules = [FakeFooModule::class])
interface TestFooComponent : FooComponent {
  fun fakeFoo(): FakeFoo
}

@Component(dependencies = [FooComponent::class])
interface BarComponent
```

This ensures Dagger code follows general engineering principles (separation of interface and
implementation). While Dagger components are interfaces, the presence of a `@Component` annotation
implicitly creates an associated implementation (the generated Dagger code); therefore, depending on
an annotated component forces a dependency on its implementation (at the build system level), and
implicitly forces test code to depend on production code. By separating them, consumers can depend
on a pure interface without needing to include the Dagger implementation in their classpath, thereby
preventing leaky abstractions, optimising build times, and directly separating production and test
code into discrete branches.

### Standard: Custom Scope Required

Components must be bound to a custom Dagger scope.

Example:

```kotlin
@FooScope
@Component
interface FooComponent {
  fun foo(): Foo
}
```

Unscoped bindings can lead to subtle bugs where expensive objects are recreated or shared state is
lost. Explicit lifecycle management ensures objects are retained only as long as needed, thereby
preventing these issues.

### Standard: Module Inclusion Restrictions

Components must only include modules defined within their own package or its subpackages; however,
they must never include modules from a subpackage if another component is defined in a package
between them.

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

Allowing components to depend on each other regardless of location promotes reuse, thereby fostering
high cohesion within packages.

### Standard: Component Suffix

Components must include the suffix `Component` in their name.

Positive example: `ConcurrencyComponent`

Negative example: `Concurrency`

This clearly distinguishes the component interface from the functionality it provides and prevents
naming collisions.

### Standard: Scope Naming Convention

The name of the custom scope associated with a component must inherit the name of the component
(minus "Component") with "Scope" appended.

Example: `FooComponent` is associated with `FooScope`.

Consistent naming allows contributors to immediately associate a scope with its component, thereby
preventing conflicts and reducing split-attention effects.

### Standard: Builder Naming

Component builders must be called `Builder`.

Example:

```kotlin
@Component
interface FooComponent {
  @Component.Builder
  interface Builder {
    @BindsInstance fun binding(bar: Bar): Builder
    fun build(): FooComponent
  }
}
```

Standardizing builder names allows engineers to predict the API surface of any component, thereby
reducing the mental overhead when switching between components.

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

Explicit naming immediately clarifies the mechanism of injection (instance binding vs component
dependency), thereby preventing collisions when binding multiple instances of the same type.

### Standard: Dependency Function Naming

Component builder functions that set component dependencies must be called `consuming`.

Example:

```kotlin
@Component(dependencies = [Bar::class])
interface FooComponent {
  @Component.Builder
  interface Builder {
    fun consuming(bar: Bar): Builder
    fun build(): FooComponent
  }
}
```

Distinct naming clearly separates structural dependencies (consuming) from runtime data (binding),
thereby making the component's initialization logic self-documenting.

### Standard: Provision Function Naming

Component provision functions must be named after the type they provide (in camelCase). However,
when bindings use qualifiers, the qualifier must be appended to the function name.

Example:

```kotlin
@Component
interface FooComponent {
  // Unqualified
  fun bar(): Bar

  // Qualified
  @Io fun genericIo(): Generic
  @Main fun genericMain(): Generic
}
```

This ensures consistency and predictability in the component's public API.

## Factory Functions

Requirements for the factory functions that instantiate components for ease of use.

### Standard: Factory Function Required

Components must have an associated factory function that instantiates the component.

Example:

```kotlin
@Component(dependencies = [Quux::class])
interface FooComponent {
  // ...
}

fun fooComponent(quux: Quux = DaggerQuux.create(), qux: Qux): FooComponent =
  DaggerFooComponent.builder()
    .consuming(quux)
    .binding(qux)
    .build()
```

This integrates cleanly with Kotlin, thereby significantly reducing the amount of manual typing
required to instantiate components.

Exception: Components that are file private may exclude the factory function (e.g. components
defined in tests for consumption in the test only).

### Standard: Default Component Dependencies

Factory functions must supply default arguments for parameters that represent component
dependencies.

Example: `fun fooComponent(quux: Quux = DaggerQuux.create(), ...)`

Providing defaults for dependencies allows consumers to focus on the parameters that actually vary,
thereby improving developer experience and reducing boilerplate.

### Practice: Production Defaults

The default arguments for component dependency parameters in factory functions should be production
components, even when the component being assembled is a test component.

Example: `fun testFooComponent(quux: Quux = DaggerQuux.create(), ...)`

This ensures tests exercise real production components and behaviours as much as possible, thereby
reducing the risk of configuration drift between test and production environments.

### Practice: Factory Function Location

Factory functions should be defined as top-level functions in the same file as the component.

Example: `fooComponent()` function in same file as `FooComponent` interface.

Co-locating the factory with the component improves discoverability.

### Practice: Factory Function Naming

Factory function names should match the component, but in lower camel case.

Example: `FooComponent` component has `fun fooComponent(...)` factory function.

This ensures factory functions can be matched to components easily.

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

### Standard: Dependency Interfaces

Modules must depend on interfaces rather than implementations.

Example: `BarModule` depends on `Baz` interface, not `BazImpl`.

This enforces consistency with the dependency inversion principle, thereby decoupling the module and
its bindings from concrete implementations.

## Testing Patterns

Patterns for defining components used in testing to ensure testability.

### Standard: Test Component Extension

Test components must extend production components.

Example: `interface TestFooComponent : FooComponent`

Tests should operate on the same interface as production code (Liskov Substitution), thereby
ensuring that the test environment accurately reflects production behavior.

### Practice: Additional Test Bindings

Test components should export additional bindings.

Example: `TestFooComponent` component extends `FooComponent` and additionally exposes
`fun testHelper(): TestHelper`.

Exposing test-specific bindings allows tests to inspect internal state or inject test doubles
without compromising the public production API, thereby facilitating white-box testing where
appropriate.

## End to End Example

The following example demonstrates a complete Dagger setup and usage that adheres to all the
directives in this document. It features upstream (User) and downstream (Profile) components,
separate modules for production and testing (including fake implementations), and strict separation
of interface and implementation via naked component interfaces.

### User Feature

Common elements:

```kotlin
/** Custom Scope */
@Scope @Retention(AnnotationRetention.RUNTIME) annotation class UserScope

/** Domain Interface */
interface User

/** Naked Component */
interface UserComponent {
  fun user(): User
}
```

Production elements:

```kotlin
/** Real Implementation */
@UserScope class RealUser @Inject constructor() : User

/** Production Module */
@Module
interface UserModule {
  @Binds fun bind(impl: RealUser): User

  companion object {
    @Provides fun provideTimeout() = 5000L
  }
}

/** Production Component */
@UserScope
@Component(modules = [UserModule::class])
interface ProdUserComponent : UserComponent {
  @Component.Builder
  interface Builder {
    fun build(): ProdUserComponent
  }
}

/** Production Factory Function */
fun userComponent(): UserComponent = DaggerProdUserComponent.builder().build()
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

/** Test Component */
@UserScope
@Component(modules = [FakeUserModule::class])
interface TestUserComponent : UserComponent {
  fun fakeUser(): FakeUser

  @Component.Builder
  interface Builder {
    fun build(): TestUserComponent
  }
}

/** Test Factory Function */
fun testUserComponent(): TestUserComponent = DaggerTestUserComponent.builder().build()
```

### Profile Feature

Common elements:

```kotlin
/** Custom Scope */
@Scope @Retention(AnnotationRetention.RUNTIME) annotation class ProfileScope

/** Domain Interface */
interface Profile

/** Naked Component */
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

/** Production Component */
@ProfileScope
@Component(dependencies = [UserComponent::class], modules = [ProfileModule::class])
interface ProdProfileComponent : ProfileComponent {
  @Component.Builder
  interface Builder {
    fun consuming(user: UserComponent): Builder
    @BindsInstance fun binding(id: ProfileId): Builder
    fun build(): ProdProfileComponent
  }
}

/** Production Factory Function */
fun profileComponent(
  user: UserComponent = userComponent(),
  id: ProfileId = ProfileId("prod-id")
): ProfileComponent =
  DaggerProdProfileComponent.builder()
    .consuming(user)
    .binding(id)
    .build()
```

Test elements:

```kotlin
/** Test Component */
@ProfileScope
@Component(dependencies = [UserComponent::class], modules = [ProfileModule::class])
interface TestProfileComponent : ProfileComponent {
  @Component.Builder
  interface Builder {
    fun consuming(user: UserComponent): Builder
    @BindsInstance fun binding(id: ProfileId): Builder
    fun build(): TestProfileComponent
  }
}

/** Test Factory Function */
fun testProfileComponent(
  user: UserComponent = userComponent(),
  id: ProfileId = ProfileId("test-id")
): TestProfileComponent =
  DaggerTestProfileComponent.builder()
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
  // 1. Setup: Create the upstream test component (provides FakeUser)
  val fakeUserComponent = testUserComponent()
  val fakeUser = fakeUserComponent.fakeUser()

  // 2. Act: Inject it into the downstream test component
  val prodProfileComponent = profileComponent(user = fakeUserComponent)
  val profile = prodProfileComponent.profile()

  // 3. Assert: Verify integration
  assertThat(profile.user).isEqualTo(fakeUser)
}
```

## Future Work

The main disadvantage of the pattern this document encodes is the need for a final downstream
assembly of components, which can become boilerplate heavy in deep graphs. A tool to reduce this
boilerplate has been designed, and implementation is tracked by
[issue 264](https://github.com/jackbradshaw/monorepo/issues/264).
