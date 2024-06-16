#!/bin/bash

repo_root=$(git rev-parse --show-toplevel)
source $repo_root/formatting.sh
ktfmt

changed_files=$(git status -s)

if [[ -z $changed_files ]]
then
  echo "Presubmit check passed: format_kotlin."
  exit 0
else
  echo "Presubmit check failed: format_kotlin."
  echo "The following files are not formatted correctly:"
  echo $changed_files
  exit 1
fi
