load("@grpc_kotlin//:kt_jvm_grpc.bzl", "kt_jvm_proto_library")
load("@protobuf//bazel:proto_library.bzl", "proto_library")
load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library", "kt_jvm_test")
load("//first_party/otter:publicity.bzl", "PUBLICITY")

proto_library(
    name = "proto",
    srcs = ["config.proto"],
    visibility = PUBLICITY,
)

kt_jvm_proto_library(
    name = "kt_jvm_proto",
    visibility = PUBLICITY,
    deps = [":proto"],
)

kt_jvm_library(
    name = "proto_extensions",
    srcs = ["ConfigExt.kt"],
    visibility = PUBLICITY,
    deps = [":kt_jvm_proto"],
)

kt_jvm_test(
    name = "test",
    srcs = ["ConfigExtTest.kt"],
    test_class = "com.jackbradshaw.otter.openxr.config.ConfigExtTest",
    deps = [
        "//first_party/otter/openxr/config:proto_extensions",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
        "@com_jackbradshaw_maven//:junit_junit",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)
