load("@io_bazel_rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("@dagger//:workspace_defs.bzl", "dagger_rules")
load("//:auto_factory.bzl", "auto_factory")

dagger_rules()

auto_factory()

kt_jvm_library(
    name = "output",
    srcs = [
        "OpenXrUserOutput.kt",
        "OpenXrUserOutputs.kt",
    ],
    visibility = [
        "//java/io/jackbradshaw/otter:__subpackages__",
        "//javatests:__subpackages__",
    ],
    deps = [
        ":auto_factory",
        "//java/io/jackbradshaw/klu/flow",
        "//java/io/jackbradshaw/otter/clock",
        "//java/io/jackbradshaw/otter/clock:qualifiers",
        "//java/io/jackbradshaw/otter/engine",
        "//java/io/jackbradshaw/otter/math:java_proto",
        "//java/io/jackbradshaw/otter/math:jmonkey_conversion",
        "//java/io/jackbradshaw/otter/math:proto_extensions",
        "//java/io/jackbradshaw/otter/openxr/model:model_java_lite_proto",
        "//java/io/jackbradshaw/otter/physics:java_proto",
        "//java/io/jackbradshaw/otter/physics:jmonkey_conversion",
        "//java/io/jackbradshaw/otter/physics:proto_extensions",
        "@maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_library(
    name = "impl",
    srcs = [
        "OpenXrUserOutputImpl.kt",
        "OpenXrUserOutputsImpl.kt",
    ],
    visibility = [
        "//java/io/jackbradshaw/otter:__subpackages__",
        "//javatests:__subpackages__",
    ],
    deps = [
        ":auto_factory",
        ":output",
        "//java/io/jackbradshaw/klu/flow",
        "//java/io/jackbradshaw/otter/clock",
        "//java/io/jackbradshaw/otter/clock:qualifiers",
        "//java/io/jackbradshaw/otter/engine",
        "//java/io/jackbradshaw/otter/math:java_proto",
        "//java/io/jackbradshaw/otter/math:jmonkey_conversion",
        "//java/io/jackbradshaw/otter/math:proto_extensions",
        "//java/io/jackbradshaw/otter/physics:java_proto",
        "//java/io/jackbradshaw/otter/physics:jmonkey_conversion",
        "//java/io/jackbradshaw/otter/physics:proto_extensions",
        "@maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_library(
    name = "module",
    srcs = [
        "OpenXrUserOutputsModule.kt",
    ],
    visibility = [
        "//java/io/jackbradshaw/otter:__subpackages__",
        "//javatests:__subpackages__",
    ],
    deps = [
        ":auto_factory",
        ":impl",
        ":output",
    ],
)
