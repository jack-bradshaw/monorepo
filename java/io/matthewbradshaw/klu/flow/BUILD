load("@io_bazel_rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")

kt_jvm_library(
    name = "flow",
    srcs = [
        "BinaryDeltaFlow.kt",
        "Flower.kt",
        "NiceFlower.kt",
    ],
    visibility = [
        "//java/io/matthewbradshaw:__subpackages__",
        "//javatests:__subpackages__",
    ],
    deps = [
        "@maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)
