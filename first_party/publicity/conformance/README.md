# Conformance Testing

Conformance tests for publicity.

## Verification Conditions

The `conformance_test` macro enforces the following conditions:

The root directory of every first party property must have a `publicity.bzl` file. The file must
contain a variable named `PUBLICITY` which is directly assigned a call to one of the functions in
[defs.bzl](/first_party/publicity/defs.bzl) (i.e. `public()`, `internal()`, `restricted()` or
`quarantined()`). Indirection is not permitted (i.e. calling the function via other functions), but
load aliases are supported (i.e. `load("//first_party/publicity:defs.bzl", foo="public")`). If the
function is `quarantined`, the package passed to the function must be the package in which the
function is called (e.g. if called in //foo/bar/publicity.bzl then "//foo/bar" must be passed in).

These rules ensure strict consistency and reduce complexity at the cost of flexibility. The rule
about quarantine is necessary because Bazel provides no way to automatically query the value, so the
conformance test ensures the correct value is passed in.

## Architecture

The conformance infrastructure is divided into several components to create a clean separation
between the underlying logic of the tests and their integration into Bazel. This approach allows the
core logic to be tested in isolation while still ensuring that the integration with Bazel is
correct. Overall this approach enables fast feedback cycles and prevents a variety of bug classes.

The main components are:

- The [package checker](/first_party/publicity/conformance/packagechecker) which validates a single
  package using a Starlark AST.
- The [workspace checker](/first_party/publicity/conformance/workspacechecker) which validates the
  entire workspace by iterating over the first party packages and delegating to the package checker.
- The [conformance test](file:///first_party/publicity/conformance/ConformanceTest.kt) which runs
  the workspace checker as a Kotlin test.

An integration test checks the conformance test by manually invoking it on a fake workspace under a
few basic conditions. It does not perform deep checking of the logic, as those details are covered
in depth by the unit tests for the package and workspace checkers.

In summary, this layered approach modularizes the verification logic, decouples it from Bazel, and
allows extensive testing for correctness and maintainability.
