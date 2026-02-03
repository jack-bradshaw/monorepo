#!/usr/bin/env bats

# Tests for the `sh_test_with_runfiles` macro.
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

  # Source shared test utilities
  source "$(rlocation "_main/first_party/bash_runfiles/tests/verify_matches_golden.sh")"
}

@test "sh_test__input_no_directive__matches_golden_no_directive" {
  verify_matches_golden \
    "_main/first_party/bash_runfiles/tests/shell/input_no_directive_test.sh" \
    "_main/first_party/bash_runfiles/tests/shell/golden_no_directive.sh"
}

@test "sh_test__input_one_directive__matches_golden_one_directive" {
  verify_matches_golden \
    "_main/first_party/bash_runfiles/tests/shell/input_one_directive_test.sh" \
    "_main/first_party/bash_runfiles/tests/shell/golden_one_directive.sh"
}

@test "sh_test__input_multiple_directives__matches_golden_multiple_directives" {
  verify_matches_golden \
    "_main/first_party/bash_runfiles/tests/shell/input_multiple_directives_test.sh" \
    "_main/first_party/bash_runfiles/tests/shell/golden_multiple_directives.sh"
}

@test "sh_test__input_malformed_directive__matches_golden_malformed_directive" {
  verify_matches_golden \
    "_main/first_party/bash_runfiles/tests/shell/input_malformed_directive_test.sh" \
    "_main/first_party/bash_runfiles/tests/shell/golden_malformed_directive.sh"
}
