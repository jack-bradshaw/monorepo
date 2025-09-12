# Build Tests

Tests for the build system and its extensions.

TODO(jack-bradshaw): Add support for everything that was added before this package was created.

## Purpose

The build system is covered by prod/test code, but changes can create coverage gaps and
opportunities for bugs. Adding tests which explicitly exercise the rules and package managers
ensures the build system is tested in isolation of prod/test code.

## Goal

The goal is as follows:

- The main rules for each language are used at least once.
- The declared deps for each package manager are used at least once.

This ensures the main rules and deps are exercised by presubmit.

## Updates

Whenever the build system is modified:

- If a new language is supported, create targets that exercise the public language rules. Each
  language has different rules, but generally aim to cover the library rules, binary rules, test
  rules, and other supplementary rules.

- If a new package manager is supported, create a target that depends on the declared deps, and if a
  new dep is added, update the target.

These are enough to ensure the build system works at a high level. Deeper tests which examine the
various behaviors of the rules are unnecessary and should not be implemented.

## Minutia

Place targets for each language into a separate package, and place tests for package managers in the
package of the language they support (e.g. Maven with Java, Cargo with Rust).

Follow these naming conventions for targets:

- Targets that check rules build are called `$targetType_must_build`.
- Targets that check tests run are called `$targetType_must_pass`.
- Targets that check deps resolve are called `$packageManager_deps_must_resolve`.

Names for sources not strongly specified, but aim to keep a clear association between sources and
rules (e.g. `Library.java` for the `java_library` rule).

File contents must be minimal and provide only enough to satisfy the build rules, meaning functions
must be empty and documentation is elided.

Avoid dependencies between test rules to keep each test (i.e. rule) independent.

All targets must have visibility private to avoid accidental usage in prod/test code.

## Example

Here is a generic example for a hypothetical `rules_foo`

In `first_party/build_tests/foo/BUILD`:

```starlark
load("@rules_foo//foo:defs.bzl", "foo_library", "foo_binary")

package(default_visibility = ["//visibility:private"])

foo_library(
    name = "foo_library_must_build",
    srcs = ["library.foo"],
)

foo_binary(
    name = "foo_binary_must_build",
    srcs = ["binary.foo"],
    main = "binary.foo",
)
```

In `first_party/build_tests/foo/library.foo`:

```
class Library {}
```
