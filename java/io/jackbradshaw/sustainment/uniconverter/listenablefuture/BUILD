load("@io_bazel_rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")

package(default_visibility = ["//visibility:public"])

kt_jvm_library(
    name = "backwards_converter",
    srcs = ["BackwardsUniConverter.kt"],
    deps = [
        "//java/io/jackbradshaw/queen/infrastructure/executors",
        "//java/io/jackbradshaw/sustainment/operations:listenablefuture",
        "//java/io/jackbradshaw/sustainment/operations:startstop",
        "//java/io/jackbradshaw/sustainment/startstop",
        "//java/io/jackbradshaw/sustainment/startstop:simplex",
        "//java/io/jackbradshaw/sustainment/uniconverter",
        "@maven//:com_google_guava_guava",
    ],
)

kt_jvm_library(
    name = "forwards_converter",
    srcs = ["ForwardsUniConverter.kt"],
    deps = [
        "//java/io/jackbradshaw/queen/infrastructure/executors",
        "//java/io/jackbradshaw/sustainment/operations:listenablefuture",
        "//java/io/jackbradshaw/sustainment/operations:startstop",
        "//java/io/jackbradshaw/sustainment/startstop",
        "//java/io/jackbradshaw/sustainment/startstop:simplex",
        "//java/io/jackbradshaw/sustainment/uniconverter",
        "@maven//:com_google_guava_guava",
    ],
)

kt_jvm_library(
    name = "pass_through_converter",
    srcs = ["PassThroughUniConverter.kt"],
    deps = [
        "//java/io/jackbradshaw/sustainment/operations:listenablefuture",
        "//java/io/jackbradshaw/sustainment/uniconverter",
    ],
)

java_library(
    name = "listenablefuture",
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
