#!/bin/bash

echo "Starting check: markdown"

# Check for sequential numbered lists (2., 3., etc.) instead of uniform numbering (1.)
if grep -rn --include="*.md" "^[2-9]\.\s\|^[0-9]\{2,\}\.\s" first_party; then
	echo "Presubmit check failed: markdown."
	echo "Found sequential numbered lists. All ordered list items must use '1.' notation."
	echo "See: first_party/contributing/documentation/markdown.md (Standard: Uniform Ordered List Numbering)"
	return 1
fi

echo "Markdown check passed."
