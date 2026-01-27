load("@bazel_bats//:rules.bzl", "bats_test")
load("//first_party/bash_runfiles:filegroup.bzl", "filegroup_with_runfiles")

filegroup_with_runfiles(
    name = "test_no_directive",
    testonly = True,
    srcs = ["input_no_directive.txt"],
)

filegroup_with_runfiles(
    name = "test_one_directive",
    testonly = True,
    srcs = ["input_one_directive.txt"],
)

filegroup_with_runfiles(
    name = "test_multiple_directives",
    testonly = True,
    srcs = ["input_multiple_directives.txt"],
)

filegroup_with_runfiles(
    name = "test_malformed_directive",
    testonly = True,
    srcs = ["input_malformed_directive.txt"],
)

bats_test(
    name = "filegroup_with_runfiles_test",
    srcs = ["filegroup_with_runfiles_test.bats"],
    data = [
        "golden_malformed_directive.txt",
        "golden_multiple_directives.txt",
        "golden_no_directive.txt",
        "golden_one_directive.txt",
        ":test_malformed_directive",
        ":test_multiple_directives",
        ":test_no_directive",
        ":test_one_directive",
        "//first_party/bash_runfiles/tests:verify_matches_golden",
        "@bazel_tools//tools/bash/runfiles",
    ],
)
