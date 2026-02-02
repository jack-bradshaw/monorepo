#!/bin/bash

{{RUNFILES_BOILERPLATE}}

# Trims the indent from a multi-line string (similar to trimIndent in Kotlin).
#
# Removes the first and last lines if they are blank (whitespace only), and removes the common
# minimal indent of all remaining lines. Reads from stdin and writes to stdout.
#
# Note: Tabs are treated as single characters. Mixing tabs and spaces in indentation will cause
# incorrect alignment because they are counted as having equal width (1 char). Use strictly tabs
# or strictly spaces for consistent results.
#
# Usage:
#
# ```bash
# local input; input="
#   formatting {
#     option = true
#   }
#
# echo "$input" | trim_indent
# "
#
# The printed value will be:
#
# "formatting {
#   option = true
# }"
#
trim_indent() {
  local input
  input=$(cat)

  if [[ -z "$input" ]]; then
    return
  fi

  local min_indent
  min_indent=$(echo "$input" |
    # Ignore lines that are empty or whitespace-only
    grep -v '^[[:space:]]*$' |
    # Extract only the leading whitespace from each line
    sed 's/^\([[:space:]]*\).*/\1/' |
    # Count the characters in that whitespace
    awk '{ print length }' |
    # Sort numerically (smallest to largest)
    sort -n |
    # Take the smallest indent length
    head -n 1)

  if [[ -z "$min_indent" ]]; then
    # Remove leading and trailing blank lines.
    echo "$input" |
      # Trim all leading blank lines
      sed '/[^[:space:]]/,$!d' |
      tac |
      # Trim all trailing blank lines (now leading)
      sed '/[^[:space:]]/,$!d' |
      tac
    return
  fi

  # Subtract indent, then remove leading and trailing blank lines.
  echo "$input" |
    # Remove min_indent from every line
    sed "s/^[[:space:]]\{$min_indent\}//" |
    # Trim all leading blank lines
    sed '/[^[:space:]]/,$!d' |
    tac |
    # Trim all trailing blank lines (now leading)
    sed '/[^[:space:]]/,$!d' |
    tac
}
