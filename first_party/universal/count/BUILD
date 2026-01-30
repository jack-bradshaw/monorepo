load("@grpc_kotlin//:kt_jvm_grpc.bzl", "kt_jvm_proto_library")
load("@protobuf//bazel:proto_library.bzl", "proto_library")

proto_library(
    name = "count",
    srcs = [
        "count.proto",
    ],
    visibility = [
        "//first_party:__subpackages__",
    ],
)

kt_jvm_proto_library(
    name = "kt_jvm_proto",
    visibility = [
        "//first_party/sasync:__subpackages__",
    ],
    deps = [":count"],
)
