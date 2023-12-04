#!/bin/bash

test_output=$(bazel test //... --test_output=errors)
all_tests_passed=grep "FAILED" && echo 0 || echo 1

if [[ $all_tests_passed ]]
then
   echo "Test code presubmit passed. All tests passed."
   exit 0
else
   echo "Test code presubmit failed. Some tests failed.
   exit 1
fi  
