load("@grpc_kotlin//:kt_jvm_grpc.bzl", "kt_jvm_proto_library")
load("@protobuf//bazel:proto_library.bzl", "proto_library")
load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library", "kt_jvm_test")

proto_library(
    name = "proto",
    srcs = [
        "config.proto",
    ],
    visibility = [
        "//java/com/jackbradshaw/otter:__subpackages__",
    ],
)

kt_jvm_proto_library(
    name = "kt_jvm_proto",
    visibility = [
        "//java/com/jackbradshaw/otter:__subpackages__",
    ],
    deps = [":proto"],
)

kt_jvm_library(
    name = "proto_extensions",
    srcs = [
        "ConfigExt.kt",
    ],
    visibility = [
        "//java/com/jackbradshaw/otter:__subpackages__",
    ],
    deps = [
        ":kt_jvm_proto",
    ],
)

kt_jvm_test(
    name = "ConfigExtTest",
    srcs = [
        "ConfigExtTest.kt",
    ],
    visibility = ["//visibility:private"],
    deps = [
        "//java/com/jackbradshaw/otter/openxr/config:proto_extensions",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
        "@com_jackbradshaw_maven//:junit_junit",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)
