# Dagger Standard

The standard for Dagger in this repository. It encodes a pattern for Dagger that scales to an
arbitrary number of modules/components, while reducing manual typing and avoiding Bazel integration
antipatterns.

## Terminology

- Component: A Dagger component interface.
- Module: A Dagger module class/interface.
- Scope: A Dagger scope annotation.

## Scope

All Dagger components and modules in `first_party` must conform to this standard.

## Components

Components must adhere to the following general requirements:

- Components must not have `Component` in their name (e.g., `Concurrency` not
  `ConcurrencyComponent`).
- Components must be bound to a custom Dagger scope (i.e. annotated with a custom Dagger scope
  annotation).
- Components may include modules from their own package and its subpackages; however, components
  must not include modules from subpackages if another component exists in a package between them. A
  component dependency must be used in that case.
- Components must each have a unique custom scope named `${componentName}Scope`, where
  `componentName` is the related component.

## Component Dependencies

Component dependencies must adhere to the following requirements:

- Components may depend on components from any package.

## Component Builders

Component builders must adhere to the following requirements:

- Component builders must be called `Builder`.
- Component builders must be nested inside the component they build.
- Component builder functions that bind instances must be called `binding`; however, when bindings
  use qualifiers, the qualifier must be appended to prevent collisions (e.g.
  `bindingIo(@Io scope: CoroutineScope)` and `bindingMain(@Main scope: CoroutineScope`).
- Component builder functions that set component dependencies must be called `consuming` (e.g.
  `consuming(foo: Foo)`).

## Test Components

Components that provide test bindings of production components must adhere to the following
requirements:

- Test components must extend production components.
- Test components may export additional bindings.

## Factory Functions

Components must have an associated factory function that instantiates the component. It must adhere
to the following requirements:

- The function must have the same name as the component, but in lower camel case.
- The function must be defined in the same file as the component.
- The function must be a top-level function.
- The function must supply default arguments for parameters that represent component dependencies.
  The defaults must match the type of the component (e.g. production components for production
  components, testing components for testing components).
- The function may supply default arguments for parameters that do not represent component
  dependencies.

Components that are exclusively consumed in the containing file may exclude the factory function
(e.g. components in tests).

## Modules

Modules must adhere to the following requirements:

- Modules must be defined in separate build targets to the objects the provide/bind.
- Modules that accept arguments in their `@Provides` function(s) must list the modules that provide
  those objects via their `includes` annotation property (if available).

## Example

Example scope:

```kotlin
@Scope @Retention(AnnotationRetention.RUNTIME) annotation class FooScope
```

Example class:

```kotlin
@FooScope
class Baz @Inject constructor(
  qux: Qux
): Bar {
  // Implementation omitted.
}
```

Example module:

```kotlin
@Module
interface BarModule {
  @Binds
  fun bind(impl: Baz): Bar
}
```

Example production component:

```kotlin
@FooScope
@Component(dependencies = [Quux::class], modules = [BarModule::class])
interface Foo {

  fun bar(): Bar

  @Component.Builder
  interface Builder {
    fun consuming(quux: Quux): Builder

    @BindsInstance fun binding(qux: Qux): Builder

    fun build(): Foo
  }
}

fun foo(quux: Quux = DaggerQuux.create(), qux: Qux) = DaggerFoo.builder().consuming(quux).binding(qux).build()
```

Example test component:

```kotlin
@Component(dependencies = [TestQuux::class])
interface TestFoo : Foo {

  @Component.Builder
  interface Builder {
     fun consuming(quux: TestQuux): Builder

    @BindsInstance fun binding(qux: Qux): Builder

    fun build(): TestFoo
  }
}

fun testFoo(quux: TestQuux = DaggerTestQuux.create(), qux: Qux) = DaggerTestFoo.builder().consuming(quux).binding(qux).build()
```
