# Presubmit

Continuous integration infrastructure.

## Release

Not released to third party package managers.

## Checks

This package contains the following checks:

- [build.sh](/first_party/presubmit/build.sh): Verifies that all targets build.
- [formatting.sh](/first_party/presubmit/formatting.sh): Verifies that all sources are in their
  [autoformatted](/first_party/formatting/README.md) state.
- [test.sh](/first_party/presubmit/test.sh): Verifies that all test targets pass.

All checks must pass for presubmit to pass.

## Usage

Presubmit is run automatically whenever a PR is opened or updated on GitHub, and the bash command to
run it locally is `source presubmit.sh; run_presubmit`.
