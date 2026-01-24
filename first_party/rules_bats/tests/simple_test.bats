#!/usr/bin/env bats

@test "basic_test_case" {
	result="hello"
	[ "$result" = "hello" ]
}
