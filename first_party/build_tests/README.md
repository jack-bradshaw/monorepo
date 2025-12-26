# Build Tests

Tests for external build rules and package managers.

## Release

Not released to third-party package managers.

## Purpose

While the external build rules and package managers are tested by other packages, changes to those
packages can create coverage gaps. This package ensures the build system remains tested, regardless
of such changes, to prevent regressions and keep the build system functional.

## Contents

This package contains tests for the build rules of the following languages/platforms:

- [Android](/first_party/build_tests/android)
- [C](/first_party/build_tests/c)
- [C++](/first_party/build_tests/cpp)
- [Go](/first_party/build_tests/go)
- [Java](/first_party/build_tests/java)
- [JavaScript](/first_party/build_tests/javascript)
- [Kotlin](/first_party/build_tests/kotlin)
- [Python](/first_party/build_tests/python)
- [Rust](/first_party/build_tests/rust)

This package contains tests for the following external package managers:

- [Cargo](/first_party/build_tests/cargo)
- [Maven](/first_party/build_tests/maven)
- [PIP](/first_party/build_tests/pip)

Tests for rules defined in this repository are not included in this package; instead, they are
colocated with the code they exercise (e.g. [rules_hugo](/first_party/rules_hugo) and
[rules_hugo/tests](/first_party/rules_hugo/tests)).

## Contributions

Accepting contributions from third parties. In addition to the repository-wide contribution
directives, all contributions to this package and its subpackages must conform to the
[Directives](directives.md).
