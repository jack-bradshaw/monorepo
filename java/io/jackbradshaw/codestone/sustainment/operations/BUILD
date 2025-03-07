load("@io_bazel_rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")

package(default_visibility = ["//visibility:public"])

kt_jvm_library(
    name = "ktcoroutine",
    srcs = ["KtCoroutineOperation.kt"],
    deps = [
        "//java/io/jackbradshaw/codestone/sustainment/primitives",
        "@maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_library(
    name = "listenablefuture",
    srcs = ["ListenableFutureOperation.kt"],
    deps = [
        "//java/io/jackbradshaw/codestone/sustainment/primitives",
        "@maven//:com_google_guava_guava",
    ],
)

kt_jvm_library(
    name = "startstop",
    srcs = ["StartStopOperation.kt"],
    deps = [
        "//java/io/jackbradshaw/codestone/sustainment/primitives",
        "//java/io/jackbradshaw/codestone/sustainment/startstop",
        "//java/io/jackbradshaw/codestone/sustainment/startstop:simplex",
    ],
)
