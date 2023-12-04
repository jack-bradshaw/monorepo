#!/bin/bash

build_output=$(bazel build //...)

build_passed = grep "Build completed successfully" && echo 1 || echo 0


if [[ $build_passed ]]
then
   echo "Build code presubmit passed. All BUILD targets build."
   exit 0
else
   echo "Build code presubmit failed. Some BUILD targets failed to build."
   exit 1
fi  