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
core logic to be tested with granular precision while still ensuring that the integration with Bazel
is correct. The main components are:

- The [package checker](/first_party/publicity/conformance/packagechecker) which validates a single
  package using a Starlark AST.
- The [workspace checker](/first_party/publicity/conformance/workspacechecker) which validates a
  single workspace by iterating over the first-party packages and delegating to the package checker.
- The [runner](/first_party/publicity/conformance/runner) which validates a single workspace and
  pipes the results into STDIO.
- The [entrypoint](/first_party/publicity/conformance) (this package) which bridges the runner to a
  JVM main function for execution. The binary is the primary enter point and must be invoked via
  `bazel run`, not `bazel test`, because tests cannot reliably locale and read the whole workspace.

This modular approach makes extensive use of dependency injection to decouple the logic from the
environment and ensure thorough testing.
