#!/bin/bash

# Set presubmit to fail if even one check fails.
set -e

repo_root=$(git rev-parse --show-toplevel)

presubmits=(format_build.sh format_kotlin.sh build.sh test.sh)
for presubmit in "${presubmits[@]}";
do
  bash $repo_root/presubmit/$presubmit
done

echo "Presubmit passed!"
