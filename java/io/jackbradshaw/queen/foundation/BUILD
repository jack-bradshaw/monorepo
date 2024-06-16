load("@io_bazel_rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")

package(default_visibility = ["//visibility:public"])

kt_jvm_library(
    name = "destination",
    srcs = [
        "Destination.kt",
    ],
    deps = [
        "//java/io/jackbradshaw/sustainment/operations:coroutine",
        "//java/io/jackbradshaw/sustainment/operations:startstop",
        "//java/io/jackbradshaw/sustainment/primitives",
        "//java/io/jackbradshaw/queen/ui/primitives",
        "@maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_library(
    name = "navigator",
    srcs = [
        "Navigator.kt",
    ],
    deps = [
        ":destination",
        "//java/io/jackbradshaw/sustainment/operations:coroutine",
        "//java/io/jackbradshaw/sustainment/operations:startstop",
        "//java/io/jackbradshaw/sustainment/primitives",
        "//java/io/jackbradshaw/queen/ui/primitives",
        "@maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_library(
    name = "coordinator",
    srcs = [
        "Coordinator.kt",
    ],
    deps = [
        ":destination",
        ":navigator",
        "//java/io/jackbradshaw/sustainment/operations:coroutine",
        "//java/io/jackbradshaw/sustainment/operations:startstop",
        "//java/io/jackbradshaw/sustainment/primitives",
        "//java/io/jackbradshaw/queen/ui/primitives",
        "@maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_library(
    name = "root",
    srcs = [
        "Root.kt",
    ],
    deps = [
        ":coordinator",
        ":destination",
        ":navigator",
        "//java/io/jackbradshaw/sustainment/operations:coroutine",
        "//java/io/jackbradshaw/sustainment/operations:startstop",
        "//java/io/jackbradshaw/sustainment/primitives",
        "//java/io/jackbradshaw/queen/ui/primitives",
        "@maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)
