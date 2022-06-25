load("@io_bazel_rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("@dagger//:workspace_defs.bzl", "dagger_rules")

dagger_rules()

kt_jvm_library(
    name = "concurrency",
    srcs = [
        "Once.kt",
    ],
    visibility = ["//java/io/matthewbradshaw:__subpackages__"],
    deps = [
        "@maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)
