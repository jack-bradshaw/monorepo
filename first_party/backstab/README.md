# Backstab

Solves the "Russian Doll" problem of assembling complex Dagger component graphs by flattening all
components into one aggregated space.

## Overview

Dagger automates dependency injection at the application layer but at the cost of manually wiring
together components. If Component A depends on Component B, which needs Component C (and so forth),
you end up writing a massive builder chain just to instantiate Component A and use its bindings. In
essence, engineers who use Dagger extensively trade off manual management of application
dependencies for manual management of Dagger components. Fortunately, dependency injection is the
exact problem Dagger exists to solve, and it can be turned on itself to solve the problem. Backstab
generates Dagger code to automate the wiring between Dagger components so engineers can focus on
application logic and component dependencies instead of manual component management. Simply annotate
a component with `@Backstab` and the glue code to wire components together will be generated for
you.

## Usage

This usage guide is focused purely on how to use Backstab without going into depth on the problems
it solved and how it works. Those details can be found in the
[design doc](/first_party/backstab/design.md).

### Build Setup

Add the plugin to a kotlin target in a `BUILD` file:

```starlark
load("//first_party/dagger:defs.bzl", "kt_jvm_library_with_dagger")

kt_jvm_library_with_dagger(
    name = "my_lib",
    srcs = ["MyComponent.kt"],
    plugins = [
        "//first_party/backstab:plugin",
    ],
    deps = [
        "//first_party/backstab/annotations:backstab",
        "//first_party/backstab/annotations:aggregate",
    ],
)
```

The target will then apply the Backstab processor to its sources.

### Common Components

Annotate your Dagger components with `@Backstab`.

```kotlin
@Backstab
@Component
interface NetworkComponent {
    fun okHttpClient(): OkHttpClient
}

@Backstab
@Component(dependencies = [NetworkComponent::class])
interface AuthComponent {
    fun authManager(): AuthManager
}
```

This generates the glue code (a Dagger module) needed to wire this component into an aggregate
graph. The generated module is always named `[ComponentName]_AggregateModule` (e.g.
`NetworkComponent_AggregateModule`).

### Defining Aggregate Components

Define an interface for your aggregate component, annotated with `@AggregateScope` and `@Component`.
Install the generated modules for the components you want to include in this graph.

```kotlin
@AggregateScope
@Component(modules = [
  NetworkComponent_AggregateModule::class,
  AuthComponent_AggregateModule::class,
  UserSessionComponent_AggregateModule::class,
  // and so on
])
interface AggregateComponent {
  // Expose the components you need
  fun applicationComponent(): ApplicationComponent
}
```

### Using Aggregate Components

Instantiate the aggregate component to access the fully wired graph.

```kotlin
val aggregate = DaggerAggregateComponent.create()
val app = aggregate.applicationComponent()
```

If you need to replace a component (e.g. for testing), simply create another aggregate component
that installs a different module (e.g. `NetworkComponent_FakeAggregateModule`).

## Caveats

There are two caveats to be aware of.

### Unsatisfied Dependencies

If any of the components in the aggregate component use `@BindsInstance`, then the aggregate
component must expose a setter for it. For example:

```kotlin
@Backstab
@Component
interface ContentComponent {
  @Component.Builder
  interface Builder {
    @BindsInstance
    fun bindContext(context: Context): Builder
    fun build(): ContentComponent
  }
}

@AggregateScope
@Component(modules = [
  NetworkComponent_AggregateModule::class,
  AuthComponent_AggregateModule::class,
])
interface AggregateComponent {
  fun applicationComponent(): ApplicationComponent

  @Component.Builder
  interface Builder {
    @BindsInstance
    fun bindContext(context: Context): Builder

    fun build(): AggregateComponent
  }
}

val aggregate = DaggerAggregateComponent.builder()
    .bindContext(appContext)
    .build()
```

Backstab manages a flat space of component dependencies, but not bound objects, so it's not
sufficient for another component in the aggregate space to provide the bound instance. Unsatisfied
dependencies must be passed into the aggregate component.

### Third Party Components

Third party components can be used in AggregateComponent too, but since their sources are
unavailable, you must define the module manually. For example:

```kotlin
@Module
object ThirdPartyComponent_AggregateModule {
  @Provides
  @AggregateScope
  fun provideThirdPartyComponent(foo: Foo): ThirdPartyComponent {
    return DaggerThirdPartyComponent.builder().setFoo(foo).build()
  }
}
```

Ensure the module uses the `AggregateScope` for correct operation.
