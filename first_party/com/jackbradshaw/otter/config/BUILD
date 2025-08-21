load("@grpc_kotlin//:kt_jvm_grpc.bzl", "kt_jvm_proto_library")
load("@protobuf//bazel:proto_library.bzl", "proto_library")
load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")

proto_library(
    name = "proto",
    srcs = [
        "config.proto",
    ],
    visibility = [
        "//first_party/com/jackbradshaw/otter:__subpackages__",
    ],
    deps = [
        "//first_party/com/jackbradshaw/otter/engine/config:proto",
        "//first_party/com/jackbradshaw/otter/openxr/config:proto",
    ],
)

kt_jvm_proto_library(
    name = "kt_jvm_proto",
    visibility = [
        "//first_party/com/jackbradshaw/otter:__subpackages__",
    ],
    deps = [":proto"],
)

kt_jvm_library(
    name = "proto_extensions",
    srcs = [
        "ConfigExt.kt",
    ],
    visibility = [
        "//first_party/com/jackbradshaw/otter:__subpackages__",
    ],
    deps = [
        ":kt_jvm_proto",
        "//first_party/com/jackbradshaw/otter/engine/config:proto_extensions",
        "//first_party/com/jackbradshaw/otter/openxr/config:proto_extensions",
    ],
)
