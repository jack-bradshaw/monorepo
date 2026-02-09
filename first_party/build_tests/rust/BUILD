load("@rules_rust//rust:defs.bzl", "rust_binary", "rust_library", "rust_proc_macro", "rust_shared_library", "rust_static_library", "rust_test")

rust_library(
    name = "rust_library_must_build",
    srcs = ["main.rs"],
)

rust_binary(
    name = "rust_binary_must_build",
    srcs = ["main.rs"],
)

rust_test(
    name = "rust_test_must_pass",
    srcs = ["test.rs"],
)

rust_shared_library(
    name = "rust_shared_library_must_build",
    srcs = ["main.rs"],
)

rust_static_library(
    name = "rust_static_library_must_build",
    srcs = ["main.rs"],
)

rust_proc_macro(
    name = "rust_proc_macro_must_build",
    srcs = ["proc_macro.rs"],
)
