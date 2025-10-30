load("@grpc_kotlin//:kt_jvm_grpc.bzl", "kt_jvm_proto_library")
load("@protobuf//bazel:proto_library.bzl", "proto_library")
load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")

proto_library(
    name = "config",
    srcs = ["config.proto"],
    visibility = ["//visibility:public"],
    deps = [
        "//first_party/sasync/transport/inbound/config",
        "//first_party/sasync/transport/outbound/config",
    ],
)

kt_jvm_proto_library(
    name = "kt_jvm_proto",
    visibility = ["//visibility:public"],
    deps = [":config"],
)

kt_jvm_library(
    name = "defaults",
    srcs = ["TransportConfigDefaults.kt"],
    visibility = ["//visibility:public"],
    deps = [
        ":kt_jvm_proto",
        "//first_party/sasync/transport/inbound/config:defaults",
        "//first_party/sasync/transport/outbound/config:defaults",
    ],
)

kt_jvm_library(
    name = "module",
    srcs = ["TransportConfigModule.kt"],
    visibility = ["//visibility:public"],
    deps = [
        ":kt_jvm_proto",
        "//:dagger",
        "//first_party/sasync/config:module",
        "//first_party/sasync/transport/inbound/config:kt_jvm_proto",
        "//first_party/sasync/transport/outbound/config:kt_jvm_proto",
    ],
)
