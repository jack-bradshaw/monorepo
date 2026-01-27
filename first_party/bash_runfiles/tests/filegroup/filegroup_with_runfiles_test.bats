#!/usr/bin/env bats

# Tests for the `filegroup_with_runfiles` macro.
#

setup() {
  # These tests verify the runfiles boilerplate preprocessor; therefore, they cannot rely on the
  # preprocessor; thus, they must manually setup runfiles. This is one of the few locations in
  # the entire repository where the boilerplate is manually required.
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

  source "$(rlocation "_main/first_party/bash_runfiles/tests/verify_matches_golden.sh")"
}

@test "filegroup__input_no_directive__matches_golden_no_directive" {
  verify_matches_golden "_main/first_party/bash_runfiles/tests/filegroup/input_no_directive_with_runfiles.txt" "_main/first_party/bash_runfiles/tests/filegroup/golden_no_directive.txt"
}

@test "filegroup__input_one_directive__matches_golden_one_directive" {
  verify_matches_golden "_main/first_party/bash_runfiles/tests/filegroup/input_one_directive_with_runfiles.txt" "_main/first_party/bash_runfiles/tests/filegroup/golden_one_directive.txt"
}

@test "filegroup__input_multiple_directives__matches_golden_multiple_directives" {
  verify_matches_golden "_main/first_party/bash_runfiles/tests/filegroup/input_multiple_directives_with_runfiles.txt" "_main/first_party/bash_runfiles/tests/filegroup/golden_multiple_directives.txt"
}

@test "filegroup__input_malformed_directive__matches_golden_malformed_directive" {
  verify_matches_golden "_main/first_party/bash_runfiles/tests/filegroup/input_malformed_directive_with_runfiles.txt" "_main/first_party/bash_runfiles/tests/filegroup/golden_malformed_directive.txt"
}
