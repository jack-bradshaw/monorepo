load("@protobuf//bazel:java_proto_library.bzl", "java_proto_library")
load("@protobuf//bazel:proto_library.bzl", "proto_library")

proto_library(
    name = "message_proto",
    srcs = ["message.proto"],
)

java_proto_library(
    name = "java_proto_library_must_build",
    deps = [":message_proto"],
)
