load("@io_bazel_rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")

kt_jvm_library(
    name = "ui",
    srcs = [
        "Frameable.kt",
    ],
    visibility = ["//java/io/matthewbradshaw/octavius:__subpackages__"],
    deps = [
        "@maven//:org_jmonkeyengine_jme3_core",
        "@maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)
