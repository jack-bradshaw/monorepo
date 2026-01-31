#!/bin/bash
{{RUNFILES_BOILERPLATE}}

set -e

# Universal wrapper to launch a Maven release with authentication.
# This script is intended to be run via 'bazel run'.

# Target to publish is passed as the first argument by the macro.
PUBLISH_TARGET="$1"

if [[ -z "$PUBLISH_TARGET" ]]; then
  echo "Error: No publish target specified to the wrapper."
  exit 1
fi

if [[ -z "$BUILD_WORKSPACE_DIRECTORY" ]]; then
  echo "Error: This script must be run via 'bazel run'."
  exit 1
fi

# Move to the workspace root to ensure 'bazel' works correctly.
cd "$BUILD_WORKSPACE_DIRECTORY"

echo "Launching release for $PUBLISH_TARGET"
echo "Enter password for Sonatype."
read -s password

bazel run --stamp \
  --define "maven_repo=${MAVEN_REPO:-https://s01.oss.sonatype.org/service/local/staging/deploy/maven2}" \
  --define "maven_user=${MAVEN_USER:-jackbradshaw}" \
  --define "maven_password=$password" \
  --define gpg_sign=true \
  "$PUBLISH_TARGET.publish"
