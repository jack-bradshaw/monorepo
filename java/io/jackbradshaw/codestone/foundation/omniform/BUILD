load("@io_bazel_rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")

package(default_visibility = ["//visibility:public"])

kt_jvm_library(
    name = "omniform",
    srcs = [
        "CoordinatorImpl.kt",
    ],
    deps = [
        "//java/io/jackbradshaw/codestone/foundation:coordinator",
        "//java/io/jackbradshaw/codestone/sustainment/omnisustainer",
        "//java/io/jackbradshaw/codestone/sustainment/omnisustainer/factoring:omnisustainers",
        "//java/io/jackbradshaw/codestone/sustainment/operations:ktcoroutine",
        "//java/io/jackbradshaw/codestone/sustainment/operations:startstop",
        "//java/io/jackbradshaw/codestone/sustainment/primitives",
        "//java/io/jackbradshaw/codestone/sustainment/startstop",
        "//java/io/jackbradshaw/codestone/sustainment/startstop:simplex",
        "//java/io/jackbradshaw/codestone/ui/primitives",
        "@maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)
