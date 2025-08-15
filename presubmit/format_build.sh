#!/bin/bash
# Presubmit check to ensure all Bazel files are formatted correctly.

echo "Starting check: format_build"

REPO_ROOT=$(git rev-parse --show-toplevel)
source $REPO_ROOT/formatting/formatting.sh
buildifier

THIRD_PARTY=$REPO_ROOT/third_party

# Ignore all changes to third_party to prevent new deps and unformatted 3P code failing presubmit.
sudo git clean -fd $THIRD_PARTY

changed_files=$(git status -s)

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
