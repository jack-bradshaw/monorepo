#!/bin/bash
# Presubmit check to ensure all tests pass.

if ! bazel test //...; then
  echo "Some tests did not pass."
  return 1
fi
