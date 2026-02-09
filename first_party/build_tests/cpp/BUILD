load("@rules_cc//cc:defs.bzl", "cc_binary", "cc_library", "cc_test")

cc_library(
    name = "cpp_library_must_build",
    srcs = ["library.cpp"],
    hdrs = ["library.h"],
)

cc_binary(
    name = "cpp_binary_must_build",
    srcs = ["main.cpp"],
)

cc_test(
    name = "cpp_test_must_pass",
    srcs = ["test.cpp"],
)
