#!/bin/bash
# Presubmit check to ensure all Kotlin files are formatted correctly.

echo "Starting check: format_kotlin"

REPO_ROOT=$(git rev-parse --show-toplevel)
source $REPO_ROOT/formatting/formatting.sh
ktfmt java
ktfmt javatests

# Ignore changes to 3P to prevent new deps and 3P code from failing presubmit.
changed_files=$(git status -s | grep -v "third_party")

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
