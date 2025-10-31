# Build Tests Standard

The standard for build tests in this repository.

## Terminology

- Language Rules: The Bazel rules for a specific programming language/platform (e.g.
  `java_library`).
- Package Manager: A tool for managing external dependencies (e.g. `maven`).
- Target: A bazel target.

## Scope

All build tests in `first_party/build_tests` must conform to this standard.

## Build Rule Tests

The build rule tests for a given language/platform must adhere to the following requirements:

- Library, binary, import and export targets must be named `${rule}_must_build` where `rule` is the
  name of the rule (e.g. `java_library_must_build`).
- Test targets must be named `${rule}_must_pass` where `rule` is the name of the rule (e.g.
  `java_test_must_pass`).
- The targets must be placed in a subpackage that matches the name of the language/platform.
- A target may exist for any build rule defined by the language/platform (e.g.
  [android](/first_party/build_tests/android/))

## Package Manager Dependency Tests

The package manager dependency tests for a given package manager must adhere to the following
requirements:

- All dependencies installed by the package manager must be referenced in a single library target.
- Targets must be named `$packageManager_deps_must_resolve` where `packageManager` is the name of
  the package manager (e.g. `maven_deps_must_resolve`).
- The target must be placed in a subpackage that matches the name of the package manager (e.g.
  [pip](/first_party/build_tests/pip/)).

## General

Targets for build rule tests and package manager tests must adhere to the following requirements:

- Targets must not depend on each other.
- Targets must share source files when a single file can satisfy the requirements for multiple
  targets (e.g. `java_library` and `java_binary` can both consume a `Stub.java` file).
- Targets may depend on external dependencies as required (e.g. `junit` in `java_test` targets).
- Targets must have private visibility.

Source files used in build rule tests and package manager tests must adhere to the following
requirements:

- Source files must contain only the content required for build/tests operations to succeed.
- Source files for test targets must contain at least one empty test to ensure the runner is
  exercised.
