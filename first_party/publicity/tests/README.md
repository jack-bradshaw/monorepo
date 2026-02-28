# Publicity Tests

This directory contains the unit tests for the pure functions defined in
[defs.bzl](/first_party/publicity/defs.bzl).

Testing the success and failure paths of pure Starlark functions involves two distinct patterns.
Non-failure paths can be tested using the simpler unit-testing systems of `skylib`, whereas failure
paths must be tested using the more complex analysis-testing systems of `rules_testing`. Both
involve considerable boilerplate, but are effectively calls to pure functions with assertions to
check the result. The difference is the phase in which the test logic actually executes: Loading for
the former, and Analysis for the latter. This is necessary because any failures in the Loading phase
would fail the build entirely, whereas failures in the Analysis phase can be captured as test
results.

Given the considerable variance in boilerplate and testing patterns between success and failure
tests, they are divided into
[defs_tests_expecting_pass.bzl](file:///Users/jack/workspaces/eng/first_party/publicity/tests/defs_tests_expecting_pass.bzl)
and
[defs_tests_expecting_failure.bzl](file:///Users/jack/workspaces/eng/first_party/publicity/tests/defs_tests_expecting_failure.bzl).
Both are mainly boilerplate to connect the underlying test logic into Bazel, but this is the
unavoidable and canonical way to test pure Starlark functions.
