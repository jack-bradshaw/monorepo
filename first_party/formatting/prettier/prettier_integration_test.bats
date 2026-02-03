#!/usr/bin/env bats

# Integration tests for Prettier.
#
# These tests ensure that the Prettier binary can be invoked from Bazel and that it correctly
# ignores files based on the .prettierignore file in the workspace root. They are focused on the
# integration, not the functionality of Prettier itself, since Prettier is a third party tool;
# however, the ignore logic is checked since it has been erroneous in the past.
#
# Bats is used to ensure the tests run in a shell environment that closely mirrors the real
# production system. Using Bats allows the tests to effectively verify the full E2E system, beginning
# with Bazel invocation and ending with a formatted file. The tests deviate from the production system
# only in the location they format, as the production system formats the actual workspace, and the
# tests format files in a temporary test directory. This is necessary since Bazel tests should not
# modify the actual workspace (for reproducibility).

setup() {
  {{RUNFILES_BOILERPLATE}}
  # Load the Prettier binary location using rlocation.
  PRETTIER_BIN="$(rlocation "_main/first_party/formatting/prettier/prettier_binary")"
  source "$(rlocation "_main/first_party/dr_bashir/strings/trim_indent_with_runfiles.sh")"
}

@test "prettier_binary__formats_simple_file" {
  local test_file="simple.js"
  echo -n "function foo(a,b){return a+b;}" >"$test_file"

  run "$PRETTIER_BIN" "$test_file"

  if [ "$status" -ne 0 ]; then
    echo "Status: $status"
    echo "Output: $output"
    return 1
  fi

  # Prettier adds a trailing newline to formatted output, but BATS 'run' captures
  # output with the final trailing newline removed.
  local expected
  expected=$(trim_indent <<<"
    function foo(a, b) {
      return a + b;
    }
")

  if [ "$output" != "$expected" ]; then
    echo "Unexpected output format."
    echo "$expected" >"$TEST_UNDECLARED_OUTPUTS_DIR/expected.js"
    echo "$output" >"$TEST_UNDECLARED_OUTPUTS_DIR/actual.js"
    echo "Expected file saved to: $TEST_UNDECLARED_OUTPUTS_DIR/expected.js"
    echo "Actual file saved to: $TEST_UNDECLARED_OUTPUTS_DIR/actual.js"
    return 1
  fi
}

@test "prettier_binary__respects_ignore_rules" {
  local test_dir="first_party/site/layouts"
  mkdir -p "$test_dir"

  local malformed="<div>  {{ if .Primary }}  <span>foo</span>  {{ end }}  </div>"
  local test_file="$test_dir/ignored.html"
  echo -n "$malformed" >"$test_file"

  run "$PRETTIER_BIN" "$test_file"

  if [ "$output" != "$malformed" ]; then
    echo "Expected file to be ignored and bit-for-bit identical."
    echo "$malformed" >"$TEST_UNDECLARED_OUTPUTS_DIR/expected_ignored.html"
    echo "$output" >"$TEST_UNDECLARED_OUTPUTS_DIR/actual_ignored.html"
    echo "Expected file saved to: $TEST_UNDECLARED_OUTPUTS_DIR/expected_ignored.html"
    echo "Actual file saved to: $TEST_UNDECLARED_OUTPUTS_DIR/actual_ignored.html"
    return 1
  fi

  [ "$status" -eq 0 ]
}
