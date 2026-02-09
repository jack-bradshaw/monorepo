# AI Generated Code Directives

Directives for AI generated code in this repository.

## Scope

All first party properties in this repository must conform to these directives; however, the
contents of [third_party](/third_party) are explicitly exempt, as they originate from external
sources.

## Guideline: Permitted

AI code generation is useful and writing all code by hand is unlikely to remain viable in the
long-term, therefore AI generated code may be submitted to main; however, it must be closely refined
by a human contributor, and it must satisfy all relevant contributing directives.

Positive example (correct implementation, appropriately documented, readable, etc):

```kotlin
/**
 * Calculates the factorial of [n]. Throws an exception if [n] is negative.
 */
fun factorial(n: Int): Long {
    require(n >= 0) { "n must be non-negative" }
    return if (n <= 1) 1 else n * factorial(n - 1)
}
```

Negative example (incorrect documentation, contains commented out code, etc):

```python
# sure, here is a function that sorts a list
def sort_list(items):
    # bubble sort is fast enough (O(n))
    for i in range(len(items)):
        for j in range(len(items)):
            if items[i] < items[j]:
                # swap elements
                temp = items[i]
                items[i] = items[j]
                items[j] = temp
    return items
    # print("debug: sorted")
```

This balances the need for quality and maintainability against the undeniable benefits of AI code
generation.

Exception: In some circumstances, refining AI generated code is not worth the effort given the
intended use case (e.g. demo projects, proof of concept, abandoned work). In these cases, the code
may be submitted but it must be quarantined (visibility restricted) to ensure it does not pollute
the codebase. See the [Council publicity definition](/first_party/council/publicity.bzl) for an
example.
