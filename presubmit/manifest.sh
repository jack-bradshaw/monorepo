#!/bin/bash

# Set presubmit to fail if even one check fails.
set -e

REPO_ROOT=$(git rev-parse --show-toplevel)
cd $REPO_ROOT/presubmit

CHECKS=(format_build.sh format_kotlin.sh build.sh test.sh)
for file in "${CHECKS[@]}";
do
  bash $file
done
