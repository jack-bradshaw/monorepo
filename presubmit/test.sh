#!/bin/bash

RESULT=$(bazel test //... --test_output=errors |\
    grep "FAILED" && echo 1 || echo 0)

if [[ $RESULT ]]
then
   echo "Presubmit stage passed: All tests pass."
   exit 0
else
   echo "Presubmit stage failed: Some tests fail."
   exit 1
fi  
