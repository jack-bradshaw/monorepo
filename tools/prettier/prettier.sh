#!/usr/bin/env bash
# Prettier runner script for Bazel
# Locates the binary in runfiles and executes it using node

# Finding the runfiles directory
if [ -z "$RUNFILES_DIR" ]; then
  if [ -d "$0.runfiles" ]; then
    RUNFILES_DIR="$0.runfiles"
  else
    RUNFILES_DIR="$(pwd)"
  fi
fi

# Looking for prettier in various possible runfiles locations
POSSIBLE_BINS=(
  "$RUNFILES_DIR/com_jackbradshaw/node_modules/prettier/bin-prettier.js"
  "$RUNFILES_DIR/node_modules/prettier/bin-prettier.js"
  "node_modules/prettier/bin-prettier.js"
)

PRETTIER_BIN=""
for b in "${POSSIBLE_BINS[@]}"; do
  if [ -f "$b" ]; then
    PRETTIER_BIN="$b"
    break
  fi
done

if [ -z "$PRETTIER_BIN" ]; then
  echo "Error: Could not find Prettier binary in runfiles."
  echo "Looked in: ${POSSIBLE_BINS[*]}"
  exit 1
fi

node "$PRETTIER_BIN" "$@"
