load("//first_party/bash_runfiles:bats_test.bzl", "bats_test_with_runfiles")
load("//first_party/bash_runfiles:shell.bzl", "sh_library_with_runfiles")

package(default_visibility = ["//visibility:public"])

sh_library_with_runfiles(
    name = "trim_indent",
    srcs = ["trim_indent.sh"],
)

bats_test_with_runfiles(
    name = "trim_indent_test",
    src = "trim_indent_test.bats",
    data = [
        ":trim_indent",
        "//first_party/dr_bashir/testing:assertions",
    ],
)
