load("@io_bazel_rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")

package(default_visibility = ["//visibility:public"])

kt_jvm_library(
    name = "omnisustainer",
    srcs = ["OmniSustainer.kt"],
    deps = [
        "//java/io/jackbradshaw/codestone/sustainment/primitives",
    ],
)

kt_jvm_library(
    name = "simplex",
    srcs = ["OmniSustainerSimplex.kt"],
    deps = [
        ":omnisustainer",
        "//java/io/jackbradshaw/codestone/infrastructure/coroutines",
        "//java/io/jackbradshaw/codestone/sustainment/omniconverter",
        "//java/io/jackbradshaw/codestone/sustainment/operations:ktcoroutine",
        "//java/io/jackbradshaw/codestone/sustainment/primitives",
        "//java/io/jackbradshaw/codestone/sustainment/startstop",
        "@maven//:javax_inject_jsr330_api",
        "@maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)
