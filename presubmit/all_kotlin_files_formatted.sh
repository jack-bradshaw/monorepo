#!/bin/bash
# Presubmit to make sure all Kotlin files are in their auto-formatted state.

REPO_ROOT=$(git rev-parse --show-toplevel)

$REPO_ROOT/tools/ktfmt java
$REPO_ROOT/ktfmt javatests

if [[ -z $(git status -s) ]]
then
  echo "All Kotlin are in their auto-formatted state."
  exit 0
else
  echo "There are unformatted Kotlin files. Run ktfmt before submitting."
  exit 1
fi
