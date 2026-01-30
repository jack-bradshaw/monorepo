load("@grpc_kotlin//:kt_jvm_grpc.bzl", "kt_jvm_proto_library")
load("@protobuf//bazel:proto_library.bzl", "proto_library")
load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("//first_party/dagger:defs.bzl", "kt_jvm_library_with_dagger")

proto_library(
    name = "config",
    srcs = [
        "config.proto",
    ],
    visibility = [
        "//visibility:public",
    ],
    deps = [
        "//first_party/sasync/inbound/config",
        "//first_party/sasync/outbound/config",
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
        "//first_party/sasync/inbound/config:default",
        "//first_party/sasync/outbound/config:default",
    ],
)

kt_jvm_library_with_dagger(
    name = "module",
    srcs = ["ConfigModule.kt"],
    visibility = [
        "//visibility:public",
    ],
    deps = [
        ":kt_jvm_proto",
        "//first_party/sasync/standard:scope",
    ],
)
