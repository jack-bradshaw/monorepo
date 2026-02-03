#!/usr/bin/env bats

# Tests for the `bats_test_with_runfiles` macro.

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

@test "bats_test__input_no_directive__matches_golden_no_directive" {
  verify_matches_golden \
    "_main/first_party/bash_runfiles/tests/bats_test/input_no_directive_with_runfiles.bats" \
    "_main/first_party/bash_runfiles/tests/bats_test/golden_no_directive.bats"
}

@test "bats_test__input_one_directive__matches_golden_one_directive" {
  verify_matches_golden \
    "_main/first_party/bash_runfiles/tests/bats_test/input_one_directive_with_runfiles.bats" \
    "_main/first_party/bash_runfiles/tests/bats_test/golden_one_directive.bats"
}

@test "bats_test__input_multiple_directives__matches_golden_multiple_directives" {
  verify_matches_golden \
    "_main/first_party/bash_runfiles/tests/bats_test/input_multiple_directives_with_runfiles.bats" \
    "_main/first_party/bash_runfiles/tests/bats_test/golden_multiple_directives.bats"
}

@test "bats_test__input_malformed_directive__matches_golden_malformed_directive" {
  verify_matches_golden \
    "_main/first_party/bash_runfiles/tests/bats_test/input_malformed_directive_with_runfiles.bats" \
    "_main/first_party/bash_runfiles/tests/bats_test/golden_malformed_directive.bats"
}
