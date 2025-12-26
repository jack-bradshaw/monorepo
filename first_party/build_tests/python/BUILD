load("@rules_python//python:defs.bzl", "py_binary", "py_library", "py_test")

package(default_visibility = ["//visibility:private"])

py_library(
    name = "py_library_must_build",
    srcs = ["library.py"],
)

py_library(
    name = "lib",
    srcs = ["library.py"],
)

py_binary(
    name = "py_binary_must_build",
    srcs = ["binary.py"],
    main = "binary.py",
    deps = [":lib"],
)

py_test(
    name = "py_test_must_pass",
    srcs = ["test.py"],
    main = "test.py",
)
