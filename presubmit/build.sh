#!/bin/bash

RESULT=$(bazel build //... |\
    grep "Build completed successfully" && echo 1 || echo 0)

if [[ $RESULT ]]
then
   echo "Presubmit stage passed: All targets successfully build."
   exit 0
else
   echo "Presubmit stage failed: Some targets fail to build."
   exit 1
fi  
