load("@dagger//:workspace_defs.bzl", "dagger_rules")
load("@grpc_kotlin//:kt_jvm_grpc.bzl", "kt_jvm_proto_library")
load("@protobuf//bazel:proto_library.bzl", "proto_library")
load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("//autofactory:autofactory.bzl", "auto_factory")

dagger_rules(repo_name = "@com_jackbradshaw_maven")

auto_factory()

proto_library(
    name = "proto",
    srcs = [
        "point.proto",
        "quaternion.proto",
        "vector.proto",
    ],
    visibility = [
        "//java/com/jackbradshaw/otter:__subpackages__",
    ],
)

kt_jvm_proto_library(
    name = "kt_jvm_proto",
    visibility = [
        "//java/com/jackbradshaw/otter:__subpackages__",
    ],
    deps = [":proto"],
)

kt_jvm_library(
    name = "proto_extensions",
    srcs = [
        "PointExt.kt",
        "QuaternionExt.kt",
        "VectorExt.kt",
    ],
    visibility = [
        "//java/com/jackbradshaw/otter:__subpackages__",
    ],
    deps = [
        ":auto_factory",
        ":kt_jvm_proto",
        "@com_jackbradshaw_maven//:org_jmonkeyengine_jme3_core",
    ],
)
