load("@io_bazel_rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")

package(default_visibility = ["//visibility:public"])

kt_jvm_library(
    name = "omnisustainer",
    srcs = ["OmniSustainer.kt"],
    deps = [
        "//java/io/jackbradshaw/sustainment/primitives",
    ],
)

kt_jvm_library(
    name = "simplex",
    srcs = ["OmniSustainerSimplex.kt"],
    deps = [
        ":omnisustainer",
        "//java/io/jackbradshaw/queen/infrastructure/coroutines",
        "//java/io/jackbradshaw/sustainment/omniconverter",
        "//java/io/jackbradshaw/sustainment/operations:coroutine",
        "//java/io/jackbradshaw/sustainment/primitives",
        "//java/io/jackbradshaw/sustainment/startstop",
        "@maven//:javax_inject_jsr330_api",
        "@maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)
