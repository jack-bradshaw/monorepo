#!/bin/bash

repo_root=$(git rev-parse --show-toplevel)

$repo_root/formatting/buildifier -r $repo_root

changed_files=$(git status -s)

if [[ -z $changed_files ]]
then
  echo "Format BUILD files presubmit passed: All files are formatted."
  exit 0
else
  echo "Format BUILD files presubmit failed: Some files are unformatted."
  exit 1
fi
