#!/bin/bash

bazel run //:buildifier -- -r .

if [[ -z $(git status -s) ]]
then
  echo "Presubmit stage passed: All build files are formatted."
  exit 0
else
  echo "Presubmit stage failed: Some build files are unformatted."
  exit 1
fi
