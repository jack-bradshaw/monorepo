load("@io_bazel_rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("@dagger//:workspace_defs.bzl", "dagger_rules")
load("//:bazel/auto_factory.bzl", "auto_factory")

dagger_rules()

auto_factory()

proto_library(
    name = "proto",
    srcs = [
        "force.proto",
        "placement.proto",
    ],
    visibility = [
        "//java/io/jackbradshaw/otter:__subpackages__",
        "//javatests:__subpackages__",
    ],
    deps = ["//java/io/jackbradshaw/otter/math/model:proto"],
)

java_proto_library(
    name = "java_proto",
    visibility = [
        "//java/io/jackbradshaw/otter:__subpackages__",
        "//javatests:__subpackages__",
    ],
    deps = [":proto"],
)

java_lite_proto_library(
    name = "java_lite_proto",
    visibility = [
        "//java/io/jackbradshaw/otter:__subpackages__",
        "//javatests:__subpackages__",
    ],
    deps = [":proto"],
)

kt_jvm_library(
    name = "proto_extensions",
    srcs = [
        "ForceExt.kt",
        "PlacementExt.kt",
    ],
    visibility = ["//java/io/jackbradshaw/otter:__subpackages__"],
    deps = [
        ":java_lite_proto",
        "//java/io/jackbradshaw/otter/math/model:java_lite_proto",
        "//java/io/jackbradshaw/otter/math/model:proto_extensions",
        "@maven//:org_jmonkeyengine_jme3_core",
    ],
)
