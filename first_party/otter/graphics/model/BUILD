load("@grpc_kotlin//:kt_jvm_grpc.bzl", "kt_jvm_proto_library")
load("@protobuf//bazel:proto_library.bzl", "proto_library")
load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("//first_party/publicity:defs.bzl", "quarantined")

PUBLICITY = quarantined("//first_party/otter/graphics/model")

proto_library(
    name = "proto",
    srcs = ["color.proto"],
    visibility = PUBLICITY,
    deps = ["//first_party/otter/math/model:proto"],
)

kt_jvm_proto_library(
    name = "kt_jvm_proto",
    visibility = PUBLICITY,
    deps = [":proto"],
)

kt_jvm_library(
    name = "proto_extensions",
    srcs = ["ColorExt.kt"],
    visibility = PUBLICITY,
    deps = [
        ":kt_jvm_proto",
        "//first_party/otter/math/model:proto_extensions",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
        "@com_jackbradshaw_maven//:org_jmonkeyengine_jme3_core",
    ],
)
