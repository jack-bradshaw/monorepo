#!/bin/bash
# Presubmit check to ensure all Bazel files are formatted correctly.

if ! bazel run //first_party/formatting; then
  echo "Auto-format failed."
  return 1
fi
