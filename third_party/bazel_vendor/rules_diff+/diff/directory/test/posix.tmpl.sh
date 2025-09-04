#!/usr/bin/env sh

# Strict shell
set -o errexit
set -o nounset

# Bazel substitutions
DIFF="{{diff}}"
A="{{a}}"
B="{{b}}"
readonly DIFF A B

# TODO: handle runfiles

# Perform the difference
"${DIFF}" -Naur "${A}" "${B}"
