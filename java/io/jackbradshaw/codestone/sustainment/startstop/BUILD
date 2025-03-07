load("@io_bazel_rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")

package(default_visibility = ["//visibility:public"])

kt_jvm_library(
    name = "startstop",
    srcs = ["StartStop.kt"],
    deps = [
        "//java/io/jackbradshaw/codestone/sustainment/primitives",
        "//java/io/jackbradshaw/codestone/ui/primitives",
    ],
)

kt_jvm_library(
    name = "simplex",
    srcs = ["StartStopSimplex.kt"],
    deps = [":startstop"],
)
