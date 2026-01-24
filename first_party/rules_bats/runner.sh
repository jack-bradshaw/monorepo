#!/bin/bash
# Runs Bats tests.
#
# Required arguments:
#
# - The path to the bats script (relative to pwd), must be present.
# - N paths to .bats tests files (relative to pwd), must be at least one.
# - N arbitrary arguments to pass to the bats script, may be empty.
#
# This script is intended for Bazel integration and has not been tested in other environments.

# Fail fast to ensure test failures are propagated to Bazel.
set -e

exec "$1" "${@:2}"
