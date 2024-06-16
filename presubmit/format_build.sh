#!/bin/bash
# Presubmit check to ensure all Bazel files are formatted correctly.

repo_root=$(git rev-parse --show-toplevel)
source $repo_root/formatting.sh
buildifier

# This file is often formatted depending on the local machine, but it's not important so ignore it.
git restore $repo_root/MODULE.bazel.lock

changed_files=$(git status -s)

if [[ -z $changed_files ]]
then
  echo "Presubmit check passed: format_build."
  return 0
else
  echo "Presubmit check failed: format_build."
  echo "The following files are not formatted correctly:"
  echo $changed_files
  return 1
fi
