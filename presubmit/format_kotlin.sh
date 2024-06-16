#!/bin/bash
# Presubmit check to ensure all Kotlin files are formatted correctly.

repo_root=$(git rev-parse --show-toplevel)
source $repo_root/formatting.sh
ktfmt

changed_files=$(git status -s)

if [[ -z $changed_files ]]
then
  echo "Presubmit check passed: format_kotlin."
  return 0
else
  echo "Presubmit check failed: format_kotlin."
  echo "The following files are not formatted correctly:"
  echo $changed_files
  return 1
fi
