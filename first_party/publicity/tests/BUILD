load(":defs_tests_expecting_failure.bzl", "all_tests_expecting_failure")
load(":defs_tests_expecting_pass.bzl", "all_tests_expecting_pass")

all_tests_expecting_pass()

all_tests_expecting_failure()

test_suite(
    name = "defs_test",
    tests = [
        ":all_test_expecting_pass",
        ":quarantined__package_without_slash_test",
        ":restricted__package_with_double_slash_test",
        ":restricted__package_with_slash_test",
    ],
)
