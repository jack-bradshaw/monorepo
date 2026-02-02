#!/bin/bash

# Utilities for assertions in tests.

# Asserts that two strings are exactly equal.
#
# Arguments:
#   $1: The actual string.
#   $2: The expected string.
# Returns:
#   0 if strings match, 1 otherwise.
assert_equals() {
  local actual="$1"
  local expected="$2"

  if [[ "$actual" != "$expected" ]]; then
    echo "Expected: '$expected'"
    echo "Actual:   '$actual'"
    return 1
  fi
}
