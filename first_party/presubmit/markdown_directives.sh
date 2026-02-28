#!/bin/bash

# Automations from first_party/contributing/documentation/markdown.md

# Automation: Ordered List Numbering
if grep -rn --include="*.md" "^[2-9]\.\s\|^[0-9]\{2,\}\.\s" first_party; then
  echo "Found sequential numbered lists. All ordered list items must use '1.' notation."
  echo "See: first_party/contributing/documentation/markdown.md (Automation: Ordered List \
	Numbering)"
  return 1
fi
