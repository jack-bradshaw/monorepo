load("@io_bazel_rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")

proto_library(
    name = "proto",
    srcs = [
        "color.proto",
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
        "ColorExt.kt",
    ],
    visibility = ["//java/io/jackbradshaw/otter:__subpackages__"],
    deps = [
        ":java_lite_proto",
        "//java/io/jackbradshaw/otter/math/model:proto_extensions",
        "@maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
        "@maven//:org_jmonkeyengine_jme3_core",
    ],
)
