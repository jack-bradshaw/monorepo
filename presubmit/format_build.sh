#!/bin/bash

repo_root=$(git rev-parse --show-toplevel)
source $repo_root/formatting.sh
buildifier

changed_files=$(git status -s)

if [[ -z $changed_files ]]
then
  echo "Presubmit check passed: format_build."
  exit 0
else
  echo "Presubmit check failed: format_build."
  echo "Some Bazel files are not formatted."
  exit 1
fi
