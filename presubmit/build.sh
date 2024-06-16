#!/bin/bash
# Presubmit check to ensure all targets build.

build_passed=$(bazel build //... |\
    grep "Build completed successfully" && echo 1 || echo 0)

if [[ $build_passed ]]
then
   echo "Presubmit check passed: build."
   exit 0
else
   echo "Presubmit check failed: build."
   echo "Some targets did not build."
   exit 1
fi  
