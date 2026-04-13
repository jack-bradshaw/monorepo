load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library", "kt_jvm_test")

package(default_visibility = ["//visibility:public"])

kt_jvm_library(
    name = "startstop",
    srcs = ["StartStop.kt"],
    deps = [
        "//first_party/codestone/lifecycle/work",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_library(
    name = "simplex",
    srcs = ["StartStopImpl.kt"],
    deps = [
        ":startstop",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
        "@com_jackbradshaw_maven//:javax_inject_javax_inject",
    ],
)

kt_jvm_library(
    name = "operation",
    exports = [":startstop"],
)

alias(
    name = "coroutine",
    actual = "//first_party/codestone/lifecycle/conversion/coroutines:coroutines",
)

alias(
    name = "listenablefuture",
    actual = "//first_party/codestone/lifecycle/conversion/listenablefutures:listenablefutures",
)

kt_jvm_test(
    name = "StartStopImplTest",
    srcs = [
        "StartStopImplTest.kt",
    ],
    deps = [
        ":operation",
        ":impl",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
        "@com_jackbradshaw_maven//:junit_junit",
    ],
)