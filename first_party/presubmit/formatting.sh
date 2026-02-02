#!/bin/bash
# Presubmit check to ensure all Bazel files are formatted correctly.

if ! bazelisk run //first_party/formatting:autoformat; then
  echo "Auto-format failed."
  return 1
fi
