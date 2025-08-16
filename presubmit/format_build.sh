#!/bin/bash
# Presubmit check to ensure all Bazel files are formatted correctly.

echo "Starting check: format_build"

REPO_ROOT=$(git rev-parse --show-toplevel)
source $REPO_ROOT/formatting/formatting.sh
buildifier

# Ignore changes to 3P to prevent new deps and 3P code from failing presubmit.
changed_files=$(git status -s | grep -v "third_party")

if [[ -z $changed_files ]]
then
  echo "Presubmit check passed: format_build."
  return 0
else
  echo "Presubmit check failed: format_build."
  echo "The following files are not formatted correctly:"
  for file in "${changed_files[@]}"
  do
    echo $file
  done
  return 1
fi
