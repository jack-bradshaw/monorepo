load("@rules_rust//rust:defs.bzl", "rust_library")

package(default_visibility = ["//visibility:private"])

rust_library(
    name = "cargo_deps_must_resolve",
    srcs = ["main.rs"],
    deps = ["@crates//:serde"],
)
