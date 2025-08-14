#!/bin/bash
# Presubmit check to ensure the dependencies needed to build all targets are vendored.

echo "Starting check: vendor"

bazel vendor //...

changed_files=$(git status -s)

if [[ -z $changed_files ]]
then
  echo "Presubmit check passed: vendor."
  return 0
else
  echo "Presubmit check failed: vendor."
  echo "Ensure all necessary dependencies are vendored and committed with \`bazel vendor //...\`"
  for i in "$changed_files[@]"
  do
    echo ${changed_files[i]}
  done
  return 1
fi
