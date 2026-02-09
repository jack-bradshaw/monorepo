#!/bin/bash

# The tests that consume this helper verify the runfiles boilerplate preprocessor; therefore, the
# helper cannot rely on the preprocessor; thus, it must manually setup runfiles. This is one of the
# few locations in the entire repository where the boilerplate is manually required.
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

source "$(rlocation "_main/first_party/dr_bashir/testing/assertions_with_runfiles.sh")"
source "$(rlocation "_main/first_party/dr_bashir/strings/trim_indent_with_runfiles.sh")"

# Verifies that a file (located via rlocation) matches a golden file.
# Reports differences and fails the test if they don't match.
#
# Args:
#   $1: The runfile-relative path to the actual file (e.g. "_main/path/to/file").
#   $2: The runfile-relative path to the golden file.
#
# Returns:
#   0 if the files match, 1 otherwise.
verify_matches_golden() {
  local actual_rpath="$1"
  local golden_rpath="$2"

  local actual=$(rlocation "$actual_rpath")
  local golden=$(rlocation "$golden_rpath")

  if [[ ! -e "$actual" ]]; then
    echo "Actual file not found: $actual (rlocation: $actual_rpath)"
    return 1
  fi

  if [[ ! -e "$golden" ]]; then
    echo "Golden file not found: $golden (rlocation: $golden_rpath)"
    return 1
  fi

  local expected_content=$(cat "$golden")
  local actual_content=$(cat "$actual")

  local expected_trimmed=$(echo "$expected_content" | trim_indent)
  local actual_trimmed=$(echo "$actual_content" | trim_indent)

  assert_equals "$expected_trimmed" "$actual_trimmed" "Expected and actual files do not match (trimmed)."
}
