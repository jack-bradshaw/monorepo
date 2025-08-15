#!/bin/bash
# Presubmit check to ensure all Kotlin files are formatted correctly.

echo "Starting check: format_kotlin"

REPO_ROOT=$(git rev-parse --show-toplevel)
source $REPO_ROOT/formatting/formatting.sh
ktfmt java
ktfmt javatests

# Ignore all changes to third_party to prevent new deps and unformatted 3P code failing presubmit.
THIRD_PARTY=$REPO_ROOT/third_party
git clean -fd $THIRD_PARTY

changed_files=$(git status -s)

if [[ -z $changed_files ]]
then
  echo "Presubmit check passed: format_kotlin."
  return 0
else
  echo "Presubmit check failed: format_kotlin."
  echo "The following files are not formatted correctly:"
  for file in "${changed_files[@]}"
  do
    echo $file
  done
  return 1
fi
