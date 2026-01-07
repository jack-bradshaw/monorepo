#!/bin/bash
# Ensures production code does not depend on experimental code.

query="somepath(set(//first_party/... - //first_party/experimental/...), \
    //first_party/experimental/...)"
deps=$(bazel query "$query" --keep_going 2>/dev/null)

if [[ ! -z "$deps" ]]; then
	echo "Presubmit check failed: check_experimental_deps."
	echo "Production code cannot depend on experimental code."
	echo "Violations found:"
	echo "$deps"
	exit 1
fi
