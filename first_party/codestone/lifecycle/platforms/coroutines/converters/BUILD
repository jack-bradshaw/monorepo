load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library", "kt_jvm_test")

package(default_visibility = ["//visibility:public"])

kt_jvm_library(
    name = "backwards_converter",
    srcs = ["BackwardsUniConverter.kt"],
    deps = [
        "//first_party/codestone/lifecycle/startstop",
        "//first_party/codestone/lifecycle/startstop:impl",
        "//first_party/codestone/lifecycle/work",
        "//first_party/codestone/lifecycle/conversion/uniconverter",
        "//first_party/codestone/lifecycle/platforms/startstop:startstop",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
        "@com_jackbradshaw_maven//:javax_inject_javax_inject",
    ],
)

kt_jvm_library(
    name = "forwards_converter",
    srcs = ["ForwardsUniConverter.kt"],
    deps = [
        "//first_party/codestone/lifecycle/startstop",
        "//first_party/codestone/lifecycle/startstop:impl",
        "//first_party/codestone/lifecycle/work",
        "//first_party/codestone/lifecycle/conversion/uniconverter",
        "//first_party/codestone/lifecycle/platforms/startstop:startstop",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_library(
    name = "pass_through_converter",
    srcs = ["PassThroughUniConverter.kt"],
    deps = [
        "//first_party/codestone/lifecycle/conversion/uniconverter",
        "//first_party/codestone/lifecycle/work",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_test(
    name = "BackwardsUniConverterTest",
    test_class = "com.jackbradshaw.codestone.lifecycle.platforms.coroutines.converters.BackwardsUniConverterTest",
    srcs = ["BackwardsUniConverterTest.kt"],
    deps = [
        ":backwards_converter",
        "//first_party/codestone/lifecycle/work",
        "//first_party/codestone/lifecycle/startstop",
        "//first_party/codestone/lifecycle/startstop:impl",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
        "@com_jackbradshaw_maven//:junit_junit",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_test(
    name = "ForwardsUniConverterTest",
    test_class = "com.jackbradshaw.codestone.lifecycle.platforms.coroutines.converters.ForwardsUniConverterTest",
    srcs = ["ForwardsUniConverterTest.kt"],
    deps = [
        ":forwards_converter",
        "//first_party/codestone/lifecycle/startstop:impl",
        "//first_party/codestone/lifecycle/startstop",
        "//first_party/codestone/lifecycle/platforms/startstop:startstop",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
        "@com_jackbradshaw_maven//:junit_junit",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_test(
    name = "PassThroughUniConverterTest",
    test_class = "com.jackbradshaw.codestone.lifecycle.platforms.coroutines.converters.PassThroughUniConverterTest",
    srcs = ["PassThroughUniConverterTest.kt"],
    deps = [
        ":pass_through_converter",
        "//first_party/codestone/lifecycle/work",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
        "@com_jackbradshaw_maven//:junit_junit",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)
