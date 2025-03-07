load("@io_bazel_rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")

package(default_visibility = ["//visibility:public"])

kt_jvm_library(
    name = "backwards_converter",
    srcs = ["BackwardsUniConverter.kt"],
    deps = [
        "//java/io/jackbradshaw/codestone/infrastructure/coroutines",
        "//java/io/jackbradshaw/codestone/sustainment/operations:ktcoroutine",
        "//java/io/jackbradshaw/codestone/sustainment/operations:startstop",
        "//java/io/jackbradshaw/codestone/sustainment/startstop",
        "//java/io/jackbradshaw/codestone/sustainment/startstop:simplex",
        "//java/io/jackbradshaw/codestone/sustainment/uniconverter",
        "@maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_library(
    name = "forwards_converter",
    srcs = ["ForwardsUniConverter.kt"],
    deps = [
        "//java/io/jackbradshaw/codestone/infrastructure/coroutines",
        "//java/io/jackbradshaw/codestone/sustainment/operations:ktcoroutine",
        "//java/io/jackbradshaw/codestone/sustainment/operations:startstop",
        "//java/io/jackbradshaw/codestone/sustainment/startstop",
        "//java/io/jackbradshaw/codestone/sustainment/uniconverter",
        "@maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_library(
    name = "pass_through_converter",
    srcs = ["PassThroughUniConverter.kt"],
    deps = [
        "//java/io/jackbradshaw/codestone/sustainment/operations:ktcoroutine",
        "//java/io/jackbradshaw/codestone/sustainment/uniconverter",
    ],
)

java_library(
    name = "ktcoroutine",
    exports = [
        ":backwards_converter",
        ":forwards_converter",
        ":pass_through_converter",
    ],
    runtime_deps = [
        ":backwards_converter",
        ":forwards_converter",
        ":pass_through_converter",
    ],
)
