load("@grpc_kotlin//:kt_jvm_grpc.bzl", "kt_jvm_proto_library")
load("@protobuf//bazel:proto_library.bzl", "proto_library")
load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")

proto_library(
    name = "config",
    srcs = [
        "config.proto",
    ],
    visibility = [
        "//first_party/sasync:__subpackages__",
    ],
    deps = [
        "//first_party/sasync/transport/config",
    ],
)

kt_jvm_proto_library(
    name = "kt_jvm_proto",
    visibility = [
        "//first_party/sasync:__subpackages__",
    ],
    deps = [":config"],
)

kt_jvm_library(
    name = "defaults",
    srcs = ["SasyncConfigDefaults.kt"],
    visibility = ["//visibility:public"],
    deps = [
        ":kt_jvm_proto",
        "//first_party/sasync/transport/config:defaults",
    ],
)

kt_jvm_library(
    name = "module",
    srcs = ["SasyncConfigModule.kt"],
    visibility = ["//visibility:public"],
    deps = [
        ":kt_jvm_proto",
        "//:dagger",
        "//first_party/sasync/transport/config:kt_jvm_proto",
    ],
)
