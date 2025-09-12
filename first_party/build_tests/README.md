# Build Tests

Tests for the build system and its extensions.

TODO(jack-bradshaw): Add support for everything that was added before this package was created.

## Purpose

The build system is covered by prod/test code, but changes can create coverage gaps and
opportunities for bugs. Adding tests which explicitly exercise the rules and package managers
ensures the build system is tested in isolation of prod/test code.

## Golden State

The golden state describe the ideal state of this package.

- The main rules for each language are used at least once.
- The declared deps for each package manager are used at least once.

To maintain this state:

- Whenever a new language is supported, create targets that exercise the public language rules. Each
  language has different rules, and what counts as a main rule is ambugious, so aim to cover the
  library rules, the binary rules, the test rules, and any other supplementary rules that are an
  essential part of the rule set in practice.

- Whenever a new package manager is supported, create a target that depends on all of its declared
  deps, and whenever a new dep is added to an existing package manager, update the target to depend
  on it.

In this state the main rules for each language and the deps for each package manager are exercised
by presubmit. Tests to verify deeper rule behaviors are not required, and its enough to simply use
each rule and use each dep.

## Standards

Follow these standards for packages:

- Place targets for each language in a separate package named `$languageName` without shortening
  (e.g. `javascript` not `js`).
- Place targets for each package manager in a separate packages (e.g. one package for all maven
  deps, one package for all pip deps).

Follow these standards for targets:

- Name targets for language rules that must build as `$targetType_must_build`.
- Name targets for language rules that must execute tests as `$targetType_must_pass`.
- Name targets for package manager deps as `$packageManager_deps_must_resolve`.
- Targets within a package must not depend on each other (to ensure tests are independent), but may
  depend on core package manager deps for critical test infra (e.g. `junit` for java).
- Targets must have private visibility (to avoid accidental use in prod and other tests).

Follow these standards for sources:

- Reuse source files where possible (e.g. `main.rs` in the [rust](/rust) directory).
- Ensure source file contents are minimal by eliding comments/documentation/implementations where
  possible.
- In tests include a single empty test case only.

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
