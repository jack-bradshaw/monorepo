#!/bin/bash
# Presubmit check to ensure all tests pass.

if ! bazelisk test //...; then
  echo "Some tests did not pass."
  return 1
fi
