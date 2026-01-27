#!/bin/bash
# Presubmit check to ensure all targets build.

echo "bazel version $(bazel version)"

if ! bazelisk build //...; then
  echo "Some targets did not build."
  return 1
fi
