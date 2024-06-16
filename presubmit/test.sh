#!/bin/bash
# Presubmit check to ensure all tests pass.

tests_passed=$(bazel test //... | grep "FAILED" && echo 0 || echo 1)

if [[ $tests_passed ]]
then
   echo "Presubmit check passed: test."
   return 0
else
   echo "Presubmit check failed: test."
   echo "Some tests did not pass."
   return 1
fi  
