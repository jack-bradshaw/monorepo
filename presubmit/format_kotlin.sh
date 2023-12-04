#!/bin/bash

repo_root=$(git rev-parse --show-toplevel)

$repo_root/formatting/ktfmt $repo_root/java
$repo_root/formatting/ktfmt $repo_root/javatests

changed_files=$(git status -s)

if [[ -z $changed_files ]]
then
  echo "Format Kotlin files presubmit passed. All files are formatted."
  exit 0
else
  echo "Format Kotlin files presubmit failed. Some files are unformatted."
  exit 1
fi
