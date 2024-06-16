load("@io_bazel_rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")

package(default_visibility = ["//visibility:public"])

kt_jvm_library(
    name = "pass_through_converter",
    srcs = ["PassThroughUniConverter.kt"],
    deps = [
        "//java/io/jackbradshaw/sustainment/operations:startstop",
        "//java/io/jackbradshaw/sustainment/uniconverter",
    ],
)

java_library(
    name = "startstop",
    exports = [
        ":pass_through_converter",
    ],
    runtime_deps = [":pass_through_converter"],
)
