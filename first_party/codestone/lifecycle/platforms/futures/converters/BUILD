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
        "@com_jackbradshaw_maven//:com_google_guava_guava",
    ],
)

kt_jvm_library(
    name = "forwards_converter",
    srcs = ["ForwardsUniConverter.kt"],
    deps = [
        "//first_party/codestone/lifecycle/startstop",
        "//first_party/codestone/lifecycle/work",
        "//first_party/codestone/lifecycle/conversion/uniconverter",
        "//first_party/codestone/lifecycle/platforms/startstop:startstop",
        "//first_party/codestone/lifecycle/platforms/futures:futures",
        "@com_jackbradshaw_maven//:com_google_guava_guava",
    ],
)

kt_jvm_library(
    name = "pass_through_converter",
    srcs = ["PassThroughUniConverter.kt"],
    deps = [
        "//first_party/codestone/lifecycle/work",
        "//first_party/codestone/lifecycle/conversion/uniconverter",
        "@com_jackbradshaw_maven//:com_google_guava_guava",
    ],
)

kt_jvm_test(
    name = "BackwardsUniConverterTest",
    test_class = "com.jackbradshaw.codestone.lifecycle.platforms.futures.converters.BackwardsUniConverterTest",
    srcs = ["BackwardsUniConverterTest.kt"],
    deps = [
        ":backwards_converter",
        "//first_party/codestone/lifecycle/work",
        "//first_party/codestone/lifecycle/startstop",
        "@com_jackbradshaw_maven//:com_google_guava_guava",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
        "@com_jackbradshaw_maven//:junit_junit",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlin_kotlin_reflect",
    ],
)

kt_jvm_test(
    name = "ForwardsUniConverterTest",
    test_class = "com.jackbradshaw.codestone.lifecycle.platforms.futures.converters.ForwardsUniConverterTest",
    srcs = ["ForwardsUniConverterTest.kt"],
    deps = [
        ":forwards_converter",
        "//first_party/codestone/lifecycle/startstop",
        "//first_party/codestone/lifecycle/startstop:impl",
        "//first_party/codestone/lifecycle/platforms/startstop",
        "@com_jackbradshaw_maven//:com_google_guava_guava",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
        "@com_jackbradshaw_maven//:junit_junit",
    ],
)

kt_jvm_test(
    name = "PassThroughUniConverterTest",
    test_class = "com.jackbradshaw.codestone.lifecycle.platforms.futures.converters.PassThroughUniConverterTest",
    srcs = ["PassThroughUniConverterTest.kt"],
    deps = [
        ":pass_through_converter",
        "//first_party/codestone/lifecycle/work",
        "@com_jackbradshaw_maven//:com_google_guava_guava",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
        "@com_jackbradshaw_maven//:junit_junit",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlin_kotlin_reflect",
    ],
)
