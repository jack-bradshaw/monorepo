load("//first_party/bash_runfiles:bats_test.bzl", "bats_test_with_runfiles")
load("//first_party/bash_runfiles:shell.bzl", "sh_library_with_runfiles")
load("//first_party/publicity:defs.bzl", "public")

PUBLICITY = public()

sh_library_with_runfiles(
    name = "assertions",
    srcs = ["assertions.sh"],
    visibility = PUBLICITY,
)

bats_test_with_runfiles(
    name = "assert_equals_test",
    src = "assert_equals_test.bats",
    data = [":assertions"],
)
