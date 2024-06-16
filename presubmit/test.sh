#!/bin/bash
# Presubmit check to ensure all tests pass.

if ! bazel test //...; then
   echo "Presubmit check failed: test."
   echo "Some tests did not pass."
   return 1
else
   echo "Presubmit check passed: test."
   return 0
fi  
