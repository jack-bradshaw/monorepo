load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")

package(default_visibility = ["//visibility:public"])

kt_jvm_library(
    name = "platform",
    srcs = ["Platform.kt"],
    deps = [
        "//first_party/codestone/lifecycle/startstop",
        "//first_party/codestone/lifecycle/conversion/uniconverter",
        "//first_party/codestone/lifecycle/conversion/operation:work",
    ],
)

kt_jvm_library(
    name = "platforms",
    srcs = ["Platforms.kt"],
    deps = [
        ":platform",
    ],
)

kt_jvm_library(
    name = "platforms_simplex",
    srcs = ["PlatformsImpl.kt"],
    deps = [
        ":platforms",
    ],
)

kt_jvm_library(
    name = "coroutine",
    srcs = ["CoroutinePlatform.kt"],
    deps = [
        ":platform",
        "//first_party/codestone/lifecycle/startstop",
        "//first_party/codestone/lifecycle/conversion/coroutines",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
        "@com_jackbradshaw_maven//:javax_inject_javax_inject",
    ],
)

kt_jvm_library(
    name = "listenablefuture",
    srcs = ["ListenableFuturePlatform.kt"],
    deps = [
        ":platform",
        "//first_party/codestone/lifecycle/startstop",
        "//first_party/codestone/lifecycle/conversion/listenablefutures",
        "@com_jackbradshaw_maven//:com_google_guava_guava",
    ],
)

kt_jvm_library(
    name = "startstop",
    srcs = ["StartStopPlatform.kt"],
    deps = [
        ":platform",
        "//first_party/codestone/lifecycle/startstop",
        "//first_party/codestone/lifecycle/conversion/operation",
    ],
)

kt_jvm_library(
    name = "workorchestrators",
    srcs = ["WorkOrchestrators.kt"],
    deps = [
        ":platform",
        ":platforms",
        ":platforms_simplex",
        "//first_party/codestone/lifecycle/conversion/multiconverter",
        "//first_party/codestone/lifecycle/conversion/multiconverter:impl",
        "//first_party/codestone/lifecycle/orchestrator:workorchestrator",
        "//first_party/codestone/lifecycle/orchestrator:impl",
        "//first_party/codestone/lifecycle/work",
        "//first_party/codestone/lifecycle/conversion/uniconverter",
        "//first_party/codestone/interaction/ui/primitives",
    ],
)

java_library(
    name = "factoring",
    exports = [
        ":workorchestrators",
        ":coroutine",
        ":listenablefuture",
        ":startstop",
    ],
)
