load("@protobuf//bazel:proto_library.bzl", "proto_library")
load("@protobuf//bazel:py_proto_library.bzl", "py_proto_library")

package(default_visibility = ["//visibility:private"])

proto_library(
    name = "message_proto",
    srcs = ["message.proto"],
)

py_proto_library(
    name = "py_proto_library_must_build",
    deps = [":message_proto"],
)
