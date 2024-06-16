#!/bin/bash
# Presubmit check to ensure all targets build.

if ! bazel build //...; then
   echo "Presubmit check failed: build."
   echo "Some targets did not build."
   return 1
else
   echo "Presubmit check passed: build."
   return 0
fi