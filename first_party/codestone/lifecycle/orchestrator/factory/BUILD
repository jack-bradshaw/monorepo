load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")

package(default_visibility = ["//visibility:public"])

kt_jvm_library(
    name = "platform",
    srcs = ["Platform.kt"],
    deps = [
        "//first_party/codestone/lifecycle/startstop",
        "//first_party/codestone/lifecycle/conversion/uniconverter",
        "//first_party/codestone/lifecycle/platforms/startstop/converters:pass_through_converter",
        "//first_party/codestone/lifecycle/platforms/startstop:startstop",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlin_kotlin_reflect",
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
    name = "platforms_impl",
    srcs = ["PlatformsImpl.kt"],
    deps = [
        ":platforms",
        "//first_party/codestone/lifecycle/platforms/coroutines:platform",
        "//first_party/codestone/lifecycle/platforms/futures:platform",
        "//first_party/codestone/lifecycle/platforms/startstop:platform",
        "@com_jackbradshaw_maven//:javax_inject_javax_inject",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
        "@com_jackbradshaw_maven//:com_google_guava_guava",
    ],
)

kt_jvm_library(
    name = "workorchestrators",
    srcs = ["WorkOrchestrators.kt"],
    deps = [
        ":platform",
        ":platforms",
        ":platforms_impl",
        "//first_party/codestone/lifecycle/conversion/multiconverter",
        "//first_party/codestone/lifecycle/conversion/multiconverter:impl",
        "//first_party/codestone/lifecycle/orchestrator:workorchestrator",
        "//first_party/codestone/lifecycle/orchestrator:impl",
        "//first_party/codestone/lifecycle/work",
        "//first_party/codestone/lifecycle/worker",
        "//first_party/codestone/lifecycle/startstop",
        "//first_party/codestone/lifecycle/conversion/uniconverter",

        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

java_library(
    name = "factory",
    exports = [
        ":workorchestrators",
    ],
)
