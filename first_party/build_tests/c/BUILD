load("@rules_cc//cc:defs.bzl", "cc_binary", "cc_library", "cc_test")

package(default_visibility = ["//visibility:private"])

cc_library(
    name = "c_library_must_build",
    srcs = ["library.c"],
    hdrs = ["library.h"],
)

cc_binary(
    name = "c_binary_must_build",
    srcs = [
        "main.c",
    ],
)

cc_test(
    name = "c_test_must_pass",
    srcs = [
        "test.c",
    ],
)
