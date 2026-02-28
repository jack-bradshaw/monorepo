load("@bazel_bats//:rules.bzl", "bats_test")
load("//first_party/bash_runfiles:bats_test.bzl", "bats_test_with_runfiles")

bats_test_with_runfiles(
    name = "test_no_directive",
    srcs = ["input_no_directive.bats"],
    tags = ["manual"],
)

bats_test_with_runfiles(
    name = "test_one_directive",
    srcs = ["input_one_directive.bats"],
    tags = ["manual"],
)

bats_test_with_runfiles(
    name = "test_multiple_directives",
    srcs = ["input_multiple_directives.bats"],
    tags = ["manual"],
)

bats_test_with_runfiles(
    name = "test_malformed_directive",
    srcs = ["input_malformed_directive.bats"],
    tags = ["manual"],
)

# Depending on the .bats_generator targets exposes the macro-processed files for validation in tests.
bats_test(
    name = "bats_test_with_runfiles_test",
    srcs = ["bats_test_with_runfiles_test.bats"],
    data = [
        "golden_malformed_directive.bats",
        "golden_multiple_directives.bats",
        "golden_no_directive.bats",
        "golden_one_directive.bats",
        ":test_malformed_directive_input_malformed_directive.bats_generator",
        ":test_multiple_directives_input_multiple_directives.bats_generator",
        ":test_no_directive_input_no_directive.bats_generator",
        ":test_one_directive_input_one_directive.bats_generator",
        "//first_party/bash_runfiles/tests:verify_matches_golden",
        "@bazel_tools//tools/bash/runfiles",
    ],
)
