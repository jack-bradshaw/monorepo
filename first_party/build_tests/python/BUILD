load("@rules_python//python:defs.bzl", "py_binary", "py_library", "py_test")

py_library(
    name = "py_library_must_build",
    srcs = ["library.py"],
)

py_binary(
    name = "py_binary_must_build",
    srcs = ["binary.py"],
    main = "binary.py",
)

py_test(
    name = "py_test_must_pass",
    srcs = ["test.py"],
    main = "test.py",
)
