#!/bin/bash
# Presubmit check to ensure all Bazel files are formatted correctly.

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
  echo "The following files are not formatted correctly:"
  echo $changed_files
  exit 1
fi
