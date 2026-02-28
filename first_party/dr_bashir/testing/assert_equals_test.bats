#!/usr/bin/env bats

setup() {
  {{RUNFILES_BOILERPLATE}}
  source "$(rlocation "_main/first_party/dr_bashir/testing/assertions_with_runfiles.sh")"
}

@test "assert_equals__empty_strings__match" {
  run assert_equals "" ""
  [ "$status" -eq 0 ]
  [ "$output" == "" ]
}

@test "assert_equals__single_char_strings__match" {
  run assert_equals "a" "a"
  [ "$status" -eq 0 ]
  [ "$output" == "" ]
}

@test "assert_equals__single_char_strings__mismatch" {
  run assert_equals "a" "b"
  [ "$status" -eq 1 ]
  [[ "$output" == *"Expected: 'b'"* ]]
  [[ "$output" == *"Actual:   'a'"* ]]
}

@test "assert_equals__short_string__match" {
  run assert_equals "foo" "foo"
  [ "$status" -eq 0 ]
  [ "$output" == "" ]
}

@test "assert_equals__short_string__mismatch" {
  run assert_equals "foo" "bar"
  [ "$status" -eq 1 ]
  [[ "$output" == *"Expected: 'bar'"* ]]
  [[ "$output" == *"Actual:   'foo'"* ]]
}

@test "assert_equals__long_string__match" {
  local str="lorem ipsum dolor sit amet"
  run assert_equals "$str" "$str"
  [ "$status" -eq 0 ]
  [ "$output" == "" ]
}

@test "assert_equals__long_string__mismatch" {
  local actual="lorem ipsum dolor sit amet"
  local expected="lorem ipsum dolor sit amet consectetur"
  run assert_equals "$actual" "$expected"
  [ "$status" -eq 1 ]
  [[ "$output" == *"Expected: '$expected'"* ]]
  [[ "$output" == *"Actual:   '$actual'"* ]]
}

@test "assert_equals__multiline__match" {
  local str="line 1
line 2"
  run assert_equals "$str" "$str"
  [ "$status" -eq 0 ]
  [ "$output" == "" ]
}

@test "assert_equals__multiline__mismatch" {
  local actual="line 1
line 2"
  local expected="line 1
line 3"
  run assert_equals "$actual" "$expected"
  [ "$status" -eq 1 ]
  [[ "$output" == *"Expected: '$expected'"* ]]
  [[ "$output" == *"Actual:   '$actual'"* ]]
}
