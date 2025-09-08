#!/bin/bash
# Presubmit check to ensure all Bazel files are formatted correctly.

echo "Starting check: formatting"

bazel run //first_party/formatting:autoformat

# Ignore changes to 3P to prevent new deps and 3P code from failing presubmit.
changed_files=$(git status -s)

if [[ -z $changed_files ]]; then
	echo "Presubmit check passed: formatting."
	return 0
else
	echo "Presubmit check failed: formatting."
	echo "The following files are not formatted correctly:"
	for file in "${changed_files[@]}"; do
		echo $file
	done
	return 1
fi
