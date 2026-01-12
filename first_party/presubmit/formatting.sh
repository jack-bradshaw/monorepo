#!/bin/bash
# Presubmit check to ensure all Bazel files are formatted correctly.

if ! bazel run //first_party/formatting:autoformat; then
	echo "Values validation failed."
	return 1
fi
