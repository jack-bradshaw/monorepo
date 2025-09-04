#! /usr/bin/env sh

# Strict shell
set -o errexit -o nounset

# Bazel replacements
EXECUTABLE="{{path}}"
readonly EXECUTABLE

# Execute!
"${EXECUTABLE}" "${@}"
