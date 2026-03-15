# Closet

A lightweight JUnit rule for automatically closing `AutoClosable` resources in tests.

## Overview

Managing closable resources in tests avoids wasted resources and reduces test flakiness; however,
manually closing resources leads to boilerplate code and in excessive cases: unreadable tests;
therefore, this rule was created to simplify automated resource closure. It is not intended for
direct use, but rather as a base type for other rules that need to provide and close resources. The system is interface-based for flexibility and thread-safe for reliability.

## Usage

Scenario: You need to build a custom test rule that manages a complex, closable object, provides it
to the test, and closes resources at the end.

You can use closet for this. Example:

```kotlin
class FooRule : ClosetRuleTemplate<Foo>() {
  override fun initialiseResource() {
    // complex initialisation logic for Foo
  }
}

class MyTest {
  
  @get:Rule
  val rule = FooRule()

  @Test
  fun test() {
    val resource = rule.get()

    assertThat(resource.foo).isEqualTo(bar())

    // Test is ending, value in rule will be closed when function exits.
  }
}
```

The ClosetRuleTest can be used to ensure your implementation of ClosetRule is correct. It's an
abstract test that all instances of ClosetRule should satisfy. Example:

```kotlin
class FooRuleTest : ClosetRuleTest<Foo>() {
  override fun instantiateObjectUnderTesting() = FooRule()
  
  override fun injectResource() = Foo()
}
```

Alternatively when you just need to manage an object created in a test, you can use the factory
function to avoid defining a concrete class. This is useful for DI supplied types. Example:

```kotlin

class MyTest {

  @Inject lateinit var foo: Foo

  init {
    DaggerComponetn.create.inject(this)
  }
  
  @get:Rule
  val rule = ClosetRuleFactory.create(Foo())

  @Test
  fun test() {
    val resource = rule.get()

    assertThat(resource.foo).isEqualTo(bar())

    // Test is ending, value in rule will be closed now.
  }
}
```

In all cases, the rule ensures the managed resource is closed when the test ends.

## Issues

Issues relating to this package and its subpackages are tagged with `closet`.

## Contributions

Open to contributions from third parties.