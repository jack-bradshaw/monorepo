#!/bin/bash

# Fail fast to avoid breaking production.
set -e

# Standard runfiles setup.
# Based on https://github.com/bazelbuild/rules_shell/blob/main/shell/runfiles/runfiles.bash.
set -uo pipefail
set +e
f=bazel_tools/tools/bash/runfiles/runfiles.bash
source "${RUNFILES_DIR:-/dev/null}/$f" 2>/dev/null ||
	source "$(grep -sm1 "^$f " "${RUNFILES_MANIFEST_FILE:-/dev/null}" | cut -f2- -d' ')" 2>/dev/null ||
	source "$0.runfiles/$f" 2>/dev/null ||
	source "$(grep -sm1 "^$f " "$0.runfiles_manifest" | cut -f2- -d' ')" 2>/dev/null ||
	source "$(grep -sm1 "^$f " "$0.exe.runfiles_manifest" | cut -f2- -d' ')" 2>/dev/null ||
	{
		echo >&2 "ERROR: cannot find $f"
		exit 1
	}
f=
set -e

echo "Checking execution environment."
if [[ -z "${RUNFILES_DIR:-}" && -z "${RUNFILES_MANIFEST_FILE:-}" ]]; then
	echo "Error: Release script must be invoked via Bazel (bazel run //first_party/site:release)."
	echo "Exiting."
	exit 1
fi
echo "Execution environment verified."

# Resolve packaged website and firebase config via runfiles.
echo "Checking artefacts in runfiles."

PACKAGED_TAR=$(rlocation "_main/first_party/site/packaged.tar")
FIREBASE_JSON=$(rlocation "_main/first_party/site/firebase.json")

if [[ ! -f "$PACKAGED_TAR" ]]; then
	echo "Error: Could not locate packaged.tar in runfiles. Exiting."
	exit 1
fi

if [[ ! -f "$FIREBASE_JSON" ]]; then
	echo "Error: Could not locate firebase.json in runfiles. Exiting."
	exit 1
fi

echo "Artefacts verified."

# Setup working directory
echo "Setting up working directory."
DEPLOY_DIR="tmp_deploy_$(date +%s)"
echo "Creating deploy dir at $DEPLOY_DIR."
mkdir -p "$DEPLOY_DIR"
echo "Working directory set up."

# Extract artefacts to working dir.
echo "Extracting artefacts to working dir."
tar -xf "$PACKAGED_TAR" -C "$DEPLOY_DIR"
cp "$FIREBASE_JSON" "$DEPLOY_DIR/"
cd "$DEPLOY_DIR"
echo "Artefacts extracted."

echo "Configuring Firebase."

npx firebase-tools login

echo "Enter the ID of the Firebase Project to deploy to."
read -r PROJECT_ID
if [[ -z "$PROJECT_ID" ]]; then
	echo "Error: Project ID cannot be empty. Exiting."
	exit 1
fi

echo "Firebase configured."

echo "Deploying to Firebase."
npx firebase-tools deploy --project "$PROJECT_ID"
echo "Deployed to Firebase."

echo "Done."
