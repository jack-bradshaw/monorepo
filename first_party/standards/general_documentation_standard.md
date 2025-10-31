# General Documentation Standard

The standard for documentation in this repository.

## Terminology

The following terms apply with specific meaning:

- Documentation: Any human-readable file that exists to provide context to contributors. Documents
  are not necessarily documentation (e.g. the contexts of [writing](/first_party/writing/)).
- Contributor: Any individual or system who/that contributes to the state of this repository.
- Consumer: Any individual or system who/that directly or indirectly uses this repository and/or the
  artefacts it produces.

## Scope

All documentation in this repository must conform to this standard. The recursive contents of
[third_party](/third_party) are excepted as the contents are sourced from third parties.

## Impersonal

Documentation must describe the contents of the repository without reference to the
contributor/consumer; however, the contributor/consumer may be referenced in the third person when
they are an integral part of the subject (e.g. when discussing readability/accessibility). This
impersonal voice keeps the focus on the contents of the repository.

Positive Example: "The `FooProvider` creates a new instance of `Foo` on every call" Negative
example: "You can get new instances of `Foo` from the `FooProvider`"

## Objective

Objective statements must be used to explain system structure and cause-effect relationships without
subjective opinions or value judgements. This ensures content is universally accurate and not
subject to debate.

Positive Example: "The `FooProvider` is not thread-safe and concurrent calls can produce duplicates"
Negative example: "Calling the `FooProvider` from multiple threads is bad practice and can break
things"

## Precise

Information must be presented in precise terms without vagueness and imprecision. This ensures
documentation remains unambiguous.

Positive Example: "`FooProvider` can produce 1024 unique `Foo` instances before producing
duplicates" Negative example: "`FooProvider` can provide many unique instances"

## Formal

Information must be presented in formal terms without colloquialism or short-hand. This ensures
documentation remains unambiguous.

Positive Example: "`FooProvider` is not thread-safe and concurrent calls may produce non-unique
values" Negative example: "Using `FooProvider` from multiple threads is playing with fire"

## Literal

Details must be provided in literal terms without unnecessary abstraction, narrative,
anthropomorphism, or other figurative constructs. This ensures documentation remains unambiguous.

Positive Example: "A `FooProvider` produces a unique instance of `Foo` on every call" Negative
example: "`FooProvider` is a like a factory warehouse for `Foo` objects, and every item is like a
snowflake"

## Current

Documentation must describe the present state of the repository without reference to future plans,
in-progress work, or processes in other systems (including human organizations). This ensures
documentation does not become inaccurate as external systems change.

Positive Example: "`FooProvider` cannot provide instances of `Bar`" Negative example: "`FooProvider`
does not provide instances of `Bar` yet but support is in-progress (ETA 2026Q1)"

## Contractual

Implementation details must be omitted from documentation where possible. This prevents duplication
of implementation details and ensures documentation remains accurate as implementation details
change.

Positive Example: "`FooProvider` provides instances of `Foo`" Negative example: "`FooProvider` uses
Dagger internally to generate new instances of `Foo` each time one is needed"

## Terse

Extraneous and redundant material must be omitted. This ensures content remains accessible for all
consumers/contributors.

Positive Example: "Implementation of `Foo` that delegates to `Bar`" Negative example: "This class,
`BarFoo` is an extension of `Foo` that internally delegates all operations to the passed in instance
of `Bar`"

## Descriptive

Descriptive statements must be used to express details without indirection. This ensures
documentation remains unambiguous.

Positive Example: "`FooProvider` creates instances of `Foo`" Negative example: "`FooProvider` does
exactly what it sounds like it does"

## Definitive

Documentation must use definitive statements and omit speculation/hedging. This ensures content is
universally accurate and not subject to debate.

Positive Example: "`FooProvider` is not thread-safe and does not guarantee uniqueness when accessed
concurrently" Negative example: "There is a slim possibility of errors if `FooProvider` is called
from multiple threads"

## Declarative

Documentation must declaratively specify desired and existing states without providing the
instructions to reach them. This is an extension of the impersonal requirement and ensures
documentation is related to the source not the maintainer/consumer.

Positive Example: "`FooProvider` must be configured prior to use" Negative example: "Make sure to
call `configure` on the `FooProvider` prior to use"

## Minutia

Grammar must adhere to the following requirements:

- American English must be used, except when referencing entities defined in other languages (e.g. a
  function from a library that uses British English).
- All spelling must be correct.
- Commas must be omitted after abbreviations (e.g. "etc.").

Verbiage must adhere to the following requirements:

- README files must be referred to as "The README" (singular), "READMEs" (plural), or "README" when
  used as a proper noun (e.g. "README Standard").
- The term "artificial intelligence" must be used instead of "AI", "machine learning", or any other
  related term; however, specific technologies and models may be referenced by name (e.g. computer
  vision, Google Gemini Pro 2.5).
- Zero-based indexing must be used, except in contexts where a different indexing convention is the
  domain convention (e.g. programming in R).
- Hierarchical relationships must be specified using super/sub terminology instead of parent/child
  (e.g. submodule and supermodule instead of child module and parent module).
