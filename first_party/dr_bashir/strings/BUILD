load("//first_party/bash_runfiles:bats_test.bzl", "bats_test_with_runfiles")
load("//first_party/bash_runfiles:shell.bzl", "sh_library_with_runfiles")
load("//first_party/dr_bashir:publicity.bzl", "PUBLICITY")

sh_library_with_runfiles(
    name = "trim_indent",
    srcs = ["trim_indent.sh"],
    visibility = PUBLICITY,
)

bats_test_with_runfiles(
    name = "trim_indent_test",
    src = "trim_indent_test.bats",
    data = [
        ":trim_indent",
        "//first_party/dr_bashir/testing:assertions",
    ],
)
