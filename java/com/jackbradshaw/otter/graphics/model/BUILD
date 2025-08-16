load("@grpc_kotlin//:kt_jvm_grpc.bzl", "kt_jvm_proto_library")
load("@protobuf//bazel:proto_library.bzl", "proto_library")
load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")

proto_library(
    name = "proto",
    srcs = [
        "color.proto",
    ],
    visibility = [
        "//java/com/jackbradshaw/otter:__subpackages__",
        "//javatests:__subpackages__",
    ],
    deps = ["//java/com/jackbradshaw/otter/math/model:proto"],
)

kt_jvm_proto_library(
    name = "kt_jvm_proto",
    visibility = [
        "//java/com/jackbradshaw/otter:__subpackages__",
        "//javatests:__subpackages__",
    ],
    deps = [":proto"],
)

kt_jvm_library(
    name = "proto_extensions",
    srcs = [
        "ColorExt.kt",
    ],
    visibility = ["//java/com/jackbradshaw/otter:__subpackages__"],
    deps = [
        ":kt_jvm_proto",
        "//java/com/jackbradshaw/otter/math/model:proto_extensions",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
        "@com_jackbradshaw_maven//:org_jmonkeyengine_jme3_core",
    ],
)
