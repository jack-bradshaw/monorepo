load("@grpc_kotlin//:kt_jvm_grpc.bzl", "kt_jvm_proto_library")
load("@protobuf//bazel:proto_library.bzl", "proto_library")
load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("//first_party/publicity:defs.bzl", "quarantined")

PUBLICITY = quarantined("//first_party/otter/engine/config")

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
