#!/usr/bin/env bats

setup() {
  {{RUNFILES_BOILERPLATE}}
  source "$(rlocation "_main/first_party/dr_bashir/testing/assertions_with_runfiles.sh")"
  source "$(rlocation "_main/first_party/dr_bashir/strings/trim_indent_with_runfiles.sh")"
}

@test "trim_indent__empty_string__returns_empty" {
  run trim_indent <<<""

  if [ "$status" -ne 0 ]; then
    return "$status"
  fi

  assert_equals "$output" ""
}

@test "trim_indent__single_blank_line_only__returns_empty" {
  local input="
  "
  run trim_indent <<<"$input"

  if [ "$status" -ne 0 ]; then
    return "$status"
  fi

  assert_equals "$output" ""
}

@test "trim_indent__multiple_blank_lines_only__returns_empty" {
  local input="

  "
  run trim_indent <<<"$input"

  if [ "$status" -ne 0 ]; then
    return "$status"
  fi

  assert_equals "$output" ""
}

@test "trim_indent__single_populated_line__indentation_1__removes_indent" {
  local input=" line 1"

  run trim_indent <<<"$input"

  if [ "$status" -ne 0 ]; then
    return "$status"
  fi

  local expected="line 1"
  assert_equals "$output" "$expected"
}

@test "trim_indent__single_populated_line__indentation_2__removes_indent" {
  local input="  line 1"

  run trim_indent <<<"$input"

  if [ "$status" -ne 0 ]; then
    return "$status"
  fi

  local expected="line 1"
  assert_equals "$output" "$expected"
}

@test "trim_indent__multiple_populated_lines__removes_indent" {
  local input="\
    line 1
      line 2
    line 3"

  run trim_indent <<<"$input"

  if [ "$status" -ne 0 ]; then
    return "$status"
  fi

  local expected="\
line 1
  line 2
line 3"
  assert_equals "$output" "$expected"
}

@test "trim_indent__multiple_populated_lines_with_internal_blank_lines__removes_indent_and_perimeter_newlines" {
  local input="
    line 1

    line 2
"

  run trim_indent <<<"$input"

  if [ "$status" -ne 0 ]; then
    return "$status"
  fi

  local expected="line 1

line 2"
  assert_equals "$output" "$expected"
}

@test "trim_indent__multiple_populated_lines_with_leading_empty_line__removes_indent_and_perimeter_newline" {
  local input="
    line 1
    line 2"

  run trim_indent <<<"$input"

  if [ "$status" -ne 0 ]; then
    return "$status"
  fi

  local expected="line 1
line 2"
  assert_equals "$output" "$expected"
}

@test "trim_indent__multiple_populated_lines_with_multiple_leading_empty_lines__removes_indent_and_perimeter_newlines" {
  local input="

    line 1
    line 2"

  run trim_indent <<<"$input"

  if [ "$status" -ne 0 ]; then
    return "$status"
  fi

  local expected="line 1
line 2"
  assert_equals "$output" "$expected"
}

@test "trim_indent__multiple_populated_lines_with_ending_empty_line__removes_indent_and_perimeter_newline" {
  local input="\
    line 1
    line 2
"

  run trim_indent <<<"$input"

  if [ "$status" -ne 0 ]; then
    return "$status"
  fi

  local expected="line 1
line 2"
  assert_equals "$output" "$expected"
}

@test "trim_indent__multiple_populated_lines_with_multiple_ending_empty_lines__removes_indent_and_perimeter_newlines" {
  local input="\
    line 1
    line 2


"

  run trim_indent <<<"$input"

  if [ "$status" -ne 0 ]; then
    return "$status"
  fi

  local expected="line 1
line 2"
  assert_equals "$output" "$expected"
}

@test "trim_indent__no_indent__returns_original" {
  local input="\
line 1
line 2"
  run trim_indent <<<"$input"

  if [ "$status" -ne 0 ]; then
    return "$status"
  fi

  assert_equals "$output" "$input"
}
