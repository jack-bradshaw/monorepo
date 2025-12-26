# Build Test Directives

Directives for build tests in this repository.

## Terminology

The term "validation target" refers to a target that exists to verify that a build rule or package
manager works as intended. Validation targets may be of any rule type and are not limited to "test"
targets (e.g. `java_test` targets). Validation targets come in two varieties, build rule validation
targets, which exercise build rules, and package manager validation targets, which exercise package
managers. Support targets are private targets that exist to satisfy the attributes of validation
targets, but are not themselves validation targets.

## Scope

All build tests in `first_party/build_tests` must conform to these directives.

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

### Practice: Coverage

Build rule validation targets should exist for every language/platform installed in the repository
(defined in [MODULE.bazel](/MODULE.bazel)). This ensures all languages/platforms remain operational.

NOTE: This directive is presently not satisfied. Tracked by
[Issue 268](https://github.com/jack-bradshaw/monorepo/issues/268).

### Practice: Granularity

Separate build rule validation targets should exist for the library, binary, and test rule types of
each language/platform.

Example: The contents of the [Java Build Tests](/first_party/build_tests/java) directory.

## Package Manager Validation Target Directives

Directives for package manager validation targets (e.g. `maven`).

### Standard: Naming

Package manager validation targets must be named `${packageManager}_deps_must_resolve`.

Example: `//first_party/build_tests/maven:maven_deps_must_resolve`.

This ensures consistent and predictable naming across all tests.

### Practice: Location

Targets should be placed in a subpackage that matches the name of the package manager.

Example: PIP tests in `first_party/build_tests/pip/` and NPM tests in
`first_party/build_tests/npm/`.

This ensures tests are organized logically by tool.

### Practice: Coverage

Package manager validation targets should exist for every package manager installed in the
repository (defined in [MODULE.bazel](/MODULE.bazel)), and they should cover every declared
dependency (but not transitive dependencies). This ensures all package managers remain operational.

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

Example: [Stub.java](/first_party/build_tests/java/Stub.java).

Rationale: This reduces clutter and keeps tests focused on the build system behavior (not code).

### Practice: Shared Sources

Targets should share source files when a single file can satisfy the requirements for multiple
validation targets.

Example: [Stub.java](/first_party/build_tests/java/Stub.java) is shared between `java_library` and
`java_binary` validation targets.

This reduces clutter and maintenance overhead.
