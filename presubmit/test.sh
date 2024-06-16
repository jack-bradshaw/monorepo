#!/bin/bash

tests_passed=$(bazel test //... | grep "FAILED" && echo 0 || echo 1)

if [[ $tests_passed ]]
then
   echo "Presubmit check passed: test."
   exit 0
else
   echo "Presubmit check failed: test."
   echo "Some tests did not pass."
   exit 1
fi  
