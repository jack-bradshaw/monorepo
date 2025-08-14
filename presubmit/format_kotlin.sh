#!/bin/bash
# Presubmit check to ensure all Kotlin files are formatted correctly.

repo_root=$(git rev-parse --show-toplevel)
source $repo_root/formatting/formatting.sh
ktfmt java
ktfmt javatests

changed_files=$(git status -s)

if [[ -z $changed_files ]]
then
  echo "Presubmit check passed: format_kotlin."
  return 0
else
  echo "Presubmit check failed: format_kotlin."
  echo "The following files are not formatted correctly:"
  for i in "$changed_files[@]"
  do
    echo ${changed_files[i]}
  done
  return 1
fi
