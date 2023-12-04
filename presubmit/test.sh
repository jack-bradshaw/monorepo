#!/bin/bash

tests_passed=$(bazel test //... | grep "FAILED" && echo 0 || echo 1)

if [[ $tests_passed ]]
then
   echo "Test code presubmit passed. All tests passed."
   exit 0
else
   echo "Test code presubmit failed. Some tests failed."
   exit 1
fi  
