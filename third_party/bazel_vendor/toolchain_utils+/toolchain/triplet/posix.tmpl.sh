#! /usr/bin/env sh

# Strict shell
set -o errexit -o nounset

# Bazel substitutions
TRIPLET="{{triplet}}"
readonly TRIPLET

# Execute!
printf '%s\n' "${TRIPLET}"
