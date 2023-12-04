#!/bin/bash

RESULT=$(bazel test //... --test_output=errors |\
    grep "FAILED" && echo 0 || echo 1)

if [[ $RESULT ]]
then
   echo "Presubmit stage passed: All tests pass."
   exit 0
else
   echo "Presubmit stage failed: Some tests fail."
   exit 1
fi  
