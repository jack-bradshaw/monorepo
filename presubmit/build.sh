#!/bin/bash

RESULT=$(bazel build //...)

if [[ $RESULT ]]
then
   echo "Presubmit stage passed: All targets successfully build."
   exit 0
else
   echo "Presubmit stage failed: Some targets fail to build."
   exit 1
fi  
