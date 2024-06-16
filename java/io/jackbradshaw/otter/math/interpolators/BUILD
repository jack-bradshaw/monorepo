load("@dagger//:workspace_defs.bzl", "dagger_rules")
load("@io_bazel_rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("//bazel:auto_factory.bzl", "auto_factory")

dagger_rules()

auto_factory()

kt_jvm_library(
    name = "interpolators",
    srcs = [
        "QuaternionInterpolator.kt",
        "QuaternionNlerpInterpolator.kt",
        "QuaternionSlerpInterpolator.kt",
    ],
    visibility = [
        "//java/io/jackbradshaw/otter:__subpackages__",
        "//javatests:__subpackages__",
    ],
    deps = [
        ":auto_factory",
        "//java/io/jackbradshaw/otter/math/model:java_lite_proto",
        "//java/io/jackbradshaw/otter/math/model:proto_extensions",
    ],
)

kt_jvm_library(
    name = "impl",
    srcs = [
        "QuaternionNlerpInterpolator.kt",
        "QuaternionSlerpInterpolator.kt",
    ],
    visibility = [
        "//java/io/jackbradshaw/otter:__subpackages__",
        "//javatests:__subpackages__",
    ],
    deps = [
        ":auto_factory",
        ":interpolators",
        "//java/io/jackbradshaw/otter/math/model:java_lite_proto",
        "//java/io/jackbradshaw/otter/math/model:proto_extensions",
    ],
)
