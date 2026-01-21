load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library", "kt_jvm_test")

package(default_visibility = ["//visibility:public"])

kt_jvm_library(
    name = "operation_types",
    srcs = ["CoroutineOperation.kt"],
    deps = [
        "//first_party/codestone/lifecycle/work",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlin_kotlin_reflect",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_library(
    name = "backwards_converter",
    srcs = ["BackwardsUniConverter.kt"],
    deps = [
        ":operation_types",
        "//first_party/codestone/lifecycle/startstop:impl",
        "//first_party/codestone/lifecycle/conversion/uniconverter",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
        "//first_party/codestone/lifecycle/conversion/operation:work",
    ],
)

kt_jvm_library(
    name = "forwards_converter",
    srcs = ["ForwardsUniConverter.kt"],
    deps = [
        ":operation_types",
        "//first_party/codestone/lifecycle/startstop:impl",
        "//first_party/codestone/lifecycle/conversion/operation:work",
        "//first_party/codestone/lifecycle/conversion/uniconverter",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_library(
    name = "pass_through_converter",
    srcs = ["PassThroughUniConverter.kt"],
    deps = [
        ":operation_types",
        "//first_party/codestone/lifecycle/conversion/uniconverter",
    ],
)


java_library(
    name = "coroutines",
    exports = [
        ":operation_types",
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

kt_jvm_test(
    name = "BackwardsUniConverterTest",
    srcs = ["BackwardsUniConverterTest.kt"],
    deps = [
        ":backwards_converter",
        ":operation_types",
        "//first_party/codestone/lifecycle/orchestrator/factory:coroutine",
        "//first_party/codestone/lifecycle/startstop",
        "//first_party/codestone/lifecycle/conversion/uniconverter",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
        "@com_jackbradshaw_maven//:junit_junit",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_test(
    name = "ForwardsUniConverterTest",
    srcs = ["ForwardsUniConverterTest.kt"],
    deps = [
        ":forwards_converter",
        ":operation_types",
        "//first_party/codestone/lifecycle/orchestrator/factory:coroutine",
        "//first_party/codestone/lifecycle/startstop",
        "//first_party/codestone/lifecycle/startstop:impl",
        "//first_party/codestone/lifecycle/conversion/uniconverter",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
        "@com_jackbradshaw_maven//:junit_junit",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_test(
    name = "PassThroughUniConverterTest",
    srcs = ["PassThroughUniConverterTest.kt"],
    deps = [
        ":pass_through_converter",
        ":operation_types",
        "//first_party/codestone/lifecycle/orchestrator/factory:coroutine",
        "//first_party/codestone/lifecycle/startstop",
        "//first_party/codestone/lifecycle/startstop:impl",
        "//first_party/codestone/lifecycle/conversion/uniconverter",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
        "@com_jackbradshaw_maven//:junit_junit",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)
