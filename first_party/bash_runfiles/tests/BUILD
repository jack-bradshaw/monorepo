load("//first_party/bash_runfiles:shell.bzl", "sh_library_with_runfiles")

sh_library(
    name = "trim_spaces",
    testonly = True,
    srcs = ["trim_spaces.sh"],
    visibility = ["//first_party/bash_runfiles/tests:__subpackages__"],
    deps = ["@bazel_tools//tools/bash/runfiles"],
)

sh_library(
    name = "verify_matches_golden",
    testonly = True,
    srcs = ["verify_matches_golden.sh"],
    data = [":trim_spaces"],
    visibility = ["//first_party/bash_runfiles/tests:__subpackages__"],
    deps = ["@bazel_tools//tools/bash/runfiles"],
)
