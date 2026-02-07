load("@grpc_kotlin//:kt_jvm_grpc.bzl", "kt_jvm_proto_library")
load("@protobuf//bazel:proto_library.bzl", "proto_library")
load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("//first_party/otter:publicity.bzl", "PUBLICITY")

proto_library(
    name = "proto",
    srcs = [
        "point.proto",
        "quaternion.proto",
        "vector.proto",
    ],
    visibility = PUBLICITY,
)

kt_jvm_proto_library(
    name = "kt_jvm_proto",
    visibility = PUBLICITY,
    deps = [":proto"],
)

kt_jvm_library(
    name = "proto_extensions",
    srcs = [
        "PointExt.kt",
        "QuaternionExt.kt",
        "VectorExt.kt",
    ],
    visibility = PUBLICITY,
    deps = [
        ":kt_jvm_proto",
        "//first_party/autofactory",
        "@com_jackbradshaw_maven//:org_jmonkeyengine_jme3_core",
    ],
)
