#!/bin/bash
# Standardized release script for JVM libraries.

# The label of the release target is passed as the first argument.
RELEASE_TARGET="$1"

if [[ -z "$RELEASE_TARGET" ]]; then
  echo "Error: No release target specified."
  exit 1
fi

echo "Enter password for Sonatype."
read -s password

bazel run --stamp \
  --define "maven_repo=$MAVEN_REPO" \
  --define "maven_user=$MAVEN_USER" \
  --define "maven_password=$password" \
  --define gpg_sign=true \
  "$RELEASE_TARGET.publish"
