#!/bin/bash

REPO_ROOT=$(git rev-parse --show-toplevel)

$REPO_ROOT/tools/ktfmt java
$REPO_ROOT/tools/ktfmt javatests

if [[ -z $(git status -s) ]]
then
  echo "Presubmit stage passed: All Kotlin files are formatted."
  exit 0
else
  echo "Presubmit stage failed: Some Kotlin files are unformatted."
  exit 1
fi
