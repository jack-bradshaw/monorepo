load("//first_party/bash_runfiles:shell.bzl", "sh_library_with_runfiles")
load("//first_party/publicity:defs.bzl", "public")

PUBLICITY = public()

sh_library(
    name = "verify_matches_golden",
    testonly = True,
    srcs = ["verify_matches_golden.sh"],
    data = [
        "//first_party/dr_bashir/strings:trim_indent",
        "//first_party/dr_bashir/testing:assertions",
    ],
    visibility = PUBLICITY,
    deps = ["@bazel_tools//tools/bash/runfiles"],
)
