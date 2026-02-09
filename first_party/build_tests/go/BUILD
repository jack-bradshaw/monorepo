load("@rules_go//go:def.bzl", "go_binary", "go_library", "go_test")

go_library(
    name = "go_library_must_build",
    srcs = ["main.go"],
)

go_binary(
    name = "go_binary_must_build",
    srcs = ["main.go"],
)

go_test(
    name = "go_test_must_pass",
    srcs = ["test.go"],
)
