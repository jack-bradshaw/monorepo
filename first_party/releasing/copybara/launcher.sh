#!/bin/bash

# Launcher for Copybara mirroring operations.

{{RUNFILES_BOILERPLATE}}

set -e

CONFIG_FILE_PATH=$(rlocation "com_jackbradshaw/$1")

if [[ -z "$BUILD_WORKSPACE_DIRECTORY" ]]; then
  echo "Error: This script must be run via 'bazel run'."
  exit 1
fi

# Replace placeholder in a temporary config file in a temp directory
# Copybara enforces 'copy.bara.sky' filename.
TMP_DIR=$(mktemp -d)
TMP_CONFIG="$TMP_DIR/copy.bara.sky"
CURRENT_REF=$(git rev-parse HEAD)
sed "s|@@MONOREPO_ROOT@@|$BUILD_WORKSPACE_DIRECTORY|g; s|@@REF@@|$CURRENT_REF|g" "$CONFIG_FILE_PATH" > "$TMP_CONFIG"

cd "$BUILD_WORKSPACE_DIRECTORY"

bazel run //first_party/releasing/copybara:copybara -- migrate "$TMP_CONFIG" "${@:2}"

rm -rf "$TMP_DIR"
