load("@protobuf//bazel:java_proto_library.bzl", "java_proto_library")
load("@protobuf//bazel:proto_library.bzl", "proto_library")
load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")

proto_library(
    name = "message_proto",
    srcs = ["message.proto"],
)

java_proto_library(
    name = "message_java_proto",
    deps = [":message_proto"],
)

kt_jvm_library(
    name = "kt_proto_library_must_build",
    srcs = ["TestProto.kt"],
    deps = [":message_java_proto"],
)
