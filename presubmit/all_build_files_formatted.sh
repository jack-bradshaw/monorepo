#!/bin/bash

bazel run //:buildifier -- -r .

if [[ -z $(git status -s) ]]
then
  echo "All BUILD are in their auto-formatted state."
  exit 0
else
  echo "There are unformatted BUILD files. Run buildifier before submitting."
  exit 1
fi
