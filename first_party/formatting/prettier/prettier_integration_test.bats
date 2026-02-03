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
  PRETTIER_BIN="$(rlocation "_main/first_party/formatting/prettier/binary")"
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

  local expected
  expected=$(trim_indent <<<"
    function foo(a, b) {
      return a + b;
    }
")

  if [ "$output" != "$expected" ]; then
    echo "File not formatted as expected."
    echo "Expected:"
    echo "$expected"
    echo "Actual:"
    echo "$output"
    return 1
  fi
}

@test "prettier_binary__respects_ignore_rules" {
  local test_dir="first_party/site/layouts"
  mkdir -p "$test_dir"

  local malformed="<div>  {{ if .Primary }}  <span>foo</span>  {{ end }}  </div>"
  local malformed_file="$test_dir/ignored.html"
  echo -n "$malformed" >"$malformed_file"

  run "$PRETTIER_BIN" "$malformed_file"

  if [ "$output" != "$malformed" ]; then
    echo "Expected file to be unchanged (ignored by formatter)."
    echo "Expected:"
    echo "$malformed"
    echo "Actual:"
    echo "$output"
    return 1
  fi
}
