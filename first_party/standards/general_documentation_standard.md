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

This standard applies to all documentation in this repository. The recursive contents of
[third_party](/third_party) are excepted as the contents are populated automatically from third
party sources.

## Impersonal

Documentation must describe the contents of the repository without reference to the
contributor/consumer. For example, "The `FooProvider` creates a new instance of `Foo` on every call"
is acceptable, whereas "You can use the `FooProvider` to get new instances of `Foo`" and "I created
the `FooProvider` so you can get instances of `Foo`" are not. This impersonal voice keeps the focus
on the contents of the repository, but exceptions are made in cases where the contributor/consumer
is an inherent aspect of the documentation (e.g. when discussing readability and accessibility).

## Objective

Objective statements must be used to explain system structure and cause-effect relationships without
subjective opinions or value judgements. For example, "The `FooProvider` is not thread-safe and
concurrent calls can produce duplicates" is acceptable, whereas "Calling the `FooProvider` from
multiple threads is bad practice and can break things" is not. This ensures content is universally
accurate and not subject to debate.

## Precise

Information must be presented in precise terms without vagueness and imprecision. For example,
"`FooProvider` can produce 1024 unique `Foo` instances before producing duplicates" is acceptable,
whereas "`FooProvider` can provide many unique instances" is not. This ensures documentation remains
unambiguous.

## Formal

Information must be presented in formal terms without colloquialism or short-hand. For example,
"`FooProvider` is not thread-safe and concurrent calls may produce non-unique values" is acceptable,
whereas "Using `FooProvider` from multiple threads is playing with fire" is not. This ensures
documentation remains unambiguous.

## Literal

Details must be provided in literal terms without unnecessary abstraction, narrative,
anthropomorphism, or other figurative constructs. For example, "A `FooProvider` produces a unique
instance of `Foo` on every call" is acceptable, whereas "`FooProvider` is a like a factory warehouse
for `Foo` objects, and every item is like a snowflake" is not. This ensures documentation remains
unambiguous.

## Current

Documentation must describe the present state of the repository without reference to future plans,
in-progress work, or processes in other systems (including human organizations). For example,
"`FooProvider` cannot provide instances of `Bar`" is acceptable, whereas "`FooProvider` does not
provide instances of `Bar` yet but support is in-progress (ETA 2026Q1)" is not. This ensures
documentation does not become inaccurate as external systems change.

## Contractual

Implementation details must be omitted from documentation where possible. For example,
"`FooProvider` provides instances of `Foo`" is acceptable, whereas "`FooProvider` uses Dagger
internally to generate new instances of `Foo` each time one is needed" is not. This prevents
duplication of implementation details and ensures documentation remains accurate as implementation
details change.

## Terse

Extraneous and redundant material must be omitted. For example, "Implementation of `Foo` that
delegates to `Bar`" is acceptable, whereas "This class, `BarFoo` is an extension of `Foo` that
internally delegates all operations to the passed in instance of `Bar`" is not. This ensures content
remains accessible for all consumers/contributors.

## Descriptive

Descriptive statements must be used to express details without indirection. For example,
"`FooProvider` creates instances of `Foo`" is acceptable, whereas "`FooProvider` does exactly what
it sounds like it does" is not. This ensures documentation remains unambiguous.

## Definitive

Documentation must use definitive statements and omit speculation/hedging. For example,
"`FooProvider` is not thread-safe and does not guarantee uniqueness when accessed concurrently" is
acceptable, whereas "There is a slim possibility of errors if `FooProvider` is called from multiple
threads" is not.

## Declarative

Documentation must declaratively specify desired and existing states without providing the
instructions to reach them. For example, "`FooProvider` must be configured prior to use" is
acceptable, whereas "Make sure to call `configure` on the `FooProvider` prior to use" is not. This
ensures documentation describes fixed states instead of transitive state transitions.

## Minutia

The grammatical requirements are as follows:

- American English must be used, except when referencing entities defined in other languages (e.g. a
  function from a library that uses British English).
- All spelling must be correct.
- Commas must be omitted after abbreviations (e.g. "etc.").

The verbiage requirements are as follows:

- README files must be referred to as "The README" (singular), "READMEs" (plural), or "README" when
  used as a proper noun (e.g. "README Standard").
- The term "artificial intelligence" must be used instead of "AI", "machine learning", or any other
  related term, with exceptions for specific models (e.g. computer vision).
- Zero-based indexing must be used, except in contexts where a different indexing convention is the
  domain convention (e.g. programming in R).
- Hierarchical relationships must be specified using super/sub terminology instead of parent/child
  (e.g. submodule and supermodule instead of child module and parent module).
