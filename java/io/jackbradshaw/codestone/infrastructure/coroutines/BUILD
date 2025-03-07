load("@io_bazel_rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")

package(default_visibility = ["//visibility:public"])

kt_jvm_library(
    name = "coroutines",
    srcs = ["CodestoneInternalCoroutines.kt"],
    deps = ["@maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core"],
)
