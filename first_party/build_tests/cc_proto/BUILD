load("@protobuf//bazel:cc_proto_library.bzl", "cc_proto_library")
load("@protobuf//bazel:proto_library.bzl", "proto_library")

proto_library(
    name = "message_proto",
    srcs = ["message.proto"],
)

cc_proto_library(
    name = "cc_proto_library_must_build",
    deps = [":message_proto"],
)
