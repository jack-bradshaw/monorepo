# Bash Runfiles Tests

This directory contains tests for the [Bash Runfiles](/first_party/bash_runfiles) macros.

## Testing Strategy

The tests use golden testing to compare the output of the macros against expected results. The real
macros and underlying logic are exercised in the real environment (Bazel) to minimise test setup and
ensure the tests are a faithful representation of production. There are three caveats to be aware
of:

1. Since some of the macros are test targets, this approach requires defining various test targets
   that are not intended to be executed as actual tests, and they would fail if they were. These
   targets are marked as `manual` to prevent execution during presubmit.
1. The tests contain extraneous content (text, tests, shebangs, etc) to ensure the preprocessor does
   not remove any content that should remain.
