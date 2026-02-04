# Code Documentation

Directives for documenting code in this repository (e.g. KDoc, Javadoc).

## Scope

All source code in this repository must conform to these directives.

## Standard: Universal Private Documentation

All functions, including `private` helper functions and implementation details, must have KDoc (or
equivalent) explaining their purpose and behavior.

Example:

```kotlin
// Positive example
/**
 * Recursively traverses the symbol table to find matching qualifiers.
 *
 * @param root The starting node.
 * @return A list of matching symbols.
 */
private fun traverse(root: Node): List<Symbol> { ... }

// Negative example
private fun traverse(root: Node): List<Symbol> { ... }
```

Documentation ensures that implementation intent is clear to future maintainers, not just consumers
of the public API. Complex logic resides in private functions, and intent is often lost without
documentation.

## Standard: Subject KDoc Pattern

In tests, the member under test must be explicitly identified with a `/** **Subject:** ... */` KDoc.

Example:

```kotlin
class MyTest {
    /** **Subject:** The instance being tested. */
    private val myInstance = MyClass()
}
```

In complex test suites with multiple helpers, fakes, and delegates, it must be instantly obvious
which object is the primary focus of the test execution.

## Standard: Dimensional Test Documentation

Test classes must explicitly document their "Testing Dimensions" in the class header. This
matricizes the test coverage.

Example:

```kotlin
/**
 * Verifies the behavior of [MyClass].
 *
 * **Testing Dimensions:**
 * * **Topology:** Shallow, Deep, Cycle.
 * * **Concurrency:** Single-threaded, Multi-threaded.
 * * **Error State:** Valid, Invalid Input, Timeout.
 */
class MyClassTest { ... }
```

Tests should explore a matrix of scenarios (e.g. Topology x Instantiation). Documenting these
dimensions prevents gaps in coverage and explains _why_ the test suite is structured the way it is.
