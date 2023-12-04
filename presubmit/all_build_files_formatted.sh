#!/bin/bash

bazel run //:buildifier -- -r .

if [[ -z $(git status -s) ]]
then
  echo "All BUILD are in their auto-formatted state."
  exit 0
else
  echo "There are unformatted BUILD files. Run buildifier before submitting."
  echo "Affected files:"
  git diff --name-only
  exit 1
fi
