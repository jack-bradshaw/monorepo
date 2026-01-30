load("@grpc_kotlin//:kt_jvm_grpc.bzl", "kt_jvm_proto_library")
load("@protobuf//bazel:proto_library.bzl", "proto_library")
load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")

proto_library(
    name = "config",
    srcs = [
        "config.proto",
    ],
    visibility = [
        "//visibility:public",
    ],
    deps = [
        "//first_party/universal/count",
        "//first_party/universal/frequency",
    ],
)

kt_jvm_proto_library(
    name = "kt_jvm_proto",
    visibility = [
        "//visibility:public",
    ],
    deps = [":config"],
)

kt_jvm_library(
    name = "default",
    srcs = ["defaultConfig.kt"],
    visibility = [
        "//visibility:public",
    ],
    deps = [
        ":kt_jvm_proto",
        "//first_party/universal/count:kt_jvm_proto",
        "//first_party/universal/frequency:kt_jvm_proto",
    ],
)
