load("@dagger//:workspace_defs.bzl", "dagger_rules")
load("@io_bazel_rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")

dagger_rules()

kt_jvm_library(
    name = "concurrency",
    srcs = [
        "Once.kt",
    ],
    visibility = [
        "//java/io/jackbradshaw:__subpackages__",
        "//javatests:__subpackages__",
    ],
    deps = [
        "@maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)
