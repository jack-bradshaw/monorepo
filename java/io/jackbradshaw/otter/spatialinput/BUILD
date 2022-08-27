load("@io_bazel_rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("@dagger//:workspace_defs.bzl", "dagger_rules")
load("//:auto_factory.bzl", "auto_factory")

dagger_rules()

auto_factory()

kt_jvm_library(
    name = "controllers",
    srcs = [
        "VrController.kt",
        "VrControllerImpl.kt",
        "VrControllers.kt",
        "VrControllersImpl.kt",
        "VrControllersModule.kt",
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
        "//java/io/jackbradshaw/otter/physics:java_proto",
        "//java/io/jackbradshaw/otter/physics:jmonkey_conversion",
        "//java/io/jackbradshaw/otter/physics:proto_extensions",
        "@maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)
