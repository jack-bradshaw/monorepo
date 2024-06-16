load("@io_bazel_rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")

package(default_visibility = ["//visibility:public"])

kt_jvm_library(
    name = "coroutine",
    srcs = ["KtCoroutineOperation.kt"],
    deps = [
        "//java/io/jackbradshaw/sustainment/primitives",
        "@maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_library(
    name = "listenablefuture",
    srcs = ["ListenableFutureOperation.kt"],
    deps = [
        "//java/io/jackbradshaw/sustainment/primitives",
        "@maven//:com_google_guava_guava",
    ],
)

kt_jvm_library(
    name = "startstop",
    srcs = ["StartStopOperation.kt"],
    deps = [
        "//java/io/jackbradshaw/sustainment/primitives",
        "//java/io/jackbradshaw/sustainment/startstop",
        "//java/io/jackbradshaw/sustainment/startstop:simplex",
    ],
)   
