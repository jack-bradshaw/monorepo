# Build Test Directives

Directives for build tests in this repository.

## Terminology

The following definitions apply throughout this document:

- Build rules: Rules for compiling, linking, or testing code for a specific language or platform.
  Examples: `java_library`, `swift_binary`, `py_test`, `proto_library`.
- Package managers: Systems that resolve and fetch external dependencies. Examples: Maven, NPM, PIP,
  Cargo.
- Validation targets: Targets that exist to verify build rules or package managers work as intended.
- Support targets: Private targets that support validation targets but are not themselves validation
  targets.

## Scope

All build tests in `first_party/build_tests` must conform to these directives.

## Practice: Coverage

Validation targets should exist for all build rules and package managers installed in the
repository.

Rationale: Build rules and package managers produce artifacts that production code depends on, and
must remain operational as the codebase evolves, even if that includes temporarily not using them.

## Build Rule Validation Target Directives

Directives for build rule validation targets (e.g. `java_library`).

### Standard: Naming

Build rule validation targets must be named `${rule_name}_must_build` for compilation-type targets
and `${rule_name}_must_pass` for test-type targets.

Positive Example: `java_library_must_build` and `java_test_must_pass`.

Negative Example: `test_java_library` or `java_library_test`.

This ensures consistent and predictable naming across all tests.

### Standard: Location

Build rule validation targets must be placed in a subpackage that matches the name of the
language/platform.

Example: `first_party/build_tests/android/` for Android and `first_party/build_tests/java/` for
Java.

This ensures tests are organized logically by domain.

### Practice: Granularity

Separate build rule validation targets should exist for the library, binary, and test rule types of
each language/platform.

Example: The contents of the [Java Build Tests](/first_party/build_tests/java) directory.

Rationale: This ensures comprehensive coverage of all rule types and catches issues specific to each
build artifact type.

## Package Manager Validation Target Directives

Directives for package manager validation targets (e.g. `maven`).

### Practice: Coverage

Package manager validation targets should exist for every package manager installed in the
repository (defined in [MODULE.bazel](/MODULE.bazel)); furthermore, they should cover every declared
dependency (but not transitive dependencies) by including them as a dep.

This ensures all package managers remain operational and all declared dependencies are resolvable.

### Standard: Naming

Package manager validation targets must be named `${packageManager}_deps_must_resolve`.

Example: `//first_party/build_tests/maven:maven_deps_must_resolve`.

This ensures consistent and predictable naming across all tests.

### Practice: Location

Targets should be placed in a subpackage that matches the name of the package manager.

Example: PIP tests in `first_party/build_tests/pip/` and NPM tests in
`first_party/build_tests/npm/`.

This ensures tests are organized logically by tool.

### Practice: Granularity

A single package manager validation target should cover all dependencies.

Example: `//first_party/build_tests/maven:maven_deps_must_resolve`.

This avoids unnecessary target clutter while ensuring all dependencies are exercised.

## General Directives

Directives applicable to all build tests.

### Standard: Visibility

Validation targets and support targets must have private visibility.

Example: `visibility = ["//visibility:private"]`.

Rationale: Build tests are internal verification tools and should not be consumed by other packages.

### Standard: Empty Test Requirement

Test targets must contain an empty test case.

Example: A test file with a single test method that asserts nothing (e.g.
`public void testPasses() {}`).

Rationale: This ensures the test runner is actually exercised and reports a real pass/fail status.

### Practice: Independence

Validation targets must not depend on each other, but support targets may be shared.

Example: `//first_party/build_tests/python` contains independent targets.

Rationale: This ensures that each test can pass/fail independently.

### Practice: Minimal Source Content

Source files must contain the minimal content required for build/test operations to succeed.

Example: [Binary.java](/first_party/build_tests/java/Binary.java) contains only the minimal `main`
method required for a Java binary.

Rationale: This reduces clutter and keeps tests focused on the build system behavior (not code).

### Practice: Shared Sources

Targets should share source files when a single file can satisfy the requirements for multiple
validation targets.

Example: [Library.java](/first_party/build_tests/java/Library.java) is shared between
`java_library_must_build` and `java_binary_must_build` validation targets.

Rationale: This reduces clutter and maintenance overhead.
