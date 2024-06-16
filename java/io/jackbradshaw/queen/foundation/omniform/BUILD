load("@io_bazel_rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")

package(default_visibility = ["//visibility:public"])

kt_jvm_library(
    name = "omniform",
    srcs = [
        "CoordinatorImpl.kt",
    ],
    deps = [
        "//java/io/jackbradshaw/queen/foundation:coordinator",
        "//java/io/jackbradshaw/sustainment/omnisustainer",
        "//java/io/jackbradshaw/sustainment/omnisustainer/factoring:omnisustainers",
        "//java/io/jackbradshaw/sustainment/operations:coroutine",
        "//java/io/jackbradshaw/sustainment/operations:startstop",
        "//java/io/jackbradshaw/sustainment/primitives",
        "//java/io/jackbradshaw/sustainment/startstop",
        "//java/io/jackbradshaw/sustainment/startstop:simplex",
        "//java/io/jackbradshaw/queen/ui/primitives",
        "@maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)
