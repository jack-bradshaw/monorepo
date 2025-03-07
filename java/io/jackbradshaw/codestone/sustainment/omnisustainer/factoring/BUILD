load("@io_bazel_rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")

package(default_visibility = ["//visibility:public"])

kt_jvm_library(
    name = "platform",
    srcs = ["Platform.kt"],
    deps = [
        "//java/io/jackbradshaw/codestone/sustainment/operations:startstop",
        "//java/io/jackbradshaw/codestone/sustainment/uniconverter",
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
    srcs = ["PlatformsSimplex.kt"],
    deps = [
        ":platforms",
    ],
)

kt_jvm_library(
    name = "ktcoroutine",
    srcs = ["KtCoroutinePlatform.kt"],
    deps = [
        ":platform",
        "//java/io/jackbradshaw/codestone/sustainment/startstop",
        "//java/io/jackbradshaw/codestone/sustainment/uniconverter/ktcoroutine",
        "@maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_library(
    name = "listenablefuture",
    srcs = ["ListenableFuturePlatform.kt"],
    deps = [
        ":platform",
        "//java/io/jackbradshaw/codestone/sustainment/startstop",
        "//java/io/jackbradshaw/codestone/sustainment/uniconverter/listenablefuture",
        "@maven//:com_google_guava_guava",
    ],
)

kt_jvm_library(
    name = "startstop",
    srcs = ["StartStopPlatform.kt"],
    deps = [
        ":platform",
        "//java/io/jackbradshaw/codestone/sustainment/startstop",
        "//java/io/jackbradshaw/codestone/sustainment/uniconverter/startstop",
    ],
)

kt_jvm_library(
    name = "omnisustainers",
    srcs = ["OmniSustainers.kt"],
    deps = [
        ":platform",
        ":platforms",
        ":platforms_simplex",
        "//java/io/jackbradshaw/codestone/sustainment/omniconverter",
        "//java/io/jackbradshaw/codestone/sustainment/omniconverter:simplex",
        "//java/io/jackbradshaw/codestone/sustainment/omnisustainer",
        "//java/io/jackbradshaw/codestone/sustainment/omnisustainer:simplex",
        "//java/io/jackbradshaw/codestone/sustainment/primitives",
        "//java/io/jackbradshaw/codestone/sustainment/uniconverter",
        "//java/io/jackbradshaw/codestone/ui/primitives",
    ],
)
