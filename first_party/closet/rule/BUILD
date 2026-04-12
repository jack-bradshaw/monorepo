load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library", "kt_jvm_test")
load("//first_party/closet:publicity.bzl", "PUBLICITY")

kt_jvm_library(
    name = "rule",
    srcs = ["ClosetRule.kt"],
    visibility = PUBLICITY,
    deps = [
        "@com_jackbradshaw_maven//:junit_junit",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_library(
    name = "template",
    srcs = ["ClosetRuleTemplate.kt"],
    visibility = PUBLICITY,
    deps = [
        ":rule",
    ],
)

kt_jvm_library(
    name = "factory",
    srcs = ["ClosetRuleFactory.kt"],
    visibility = PUBLICITY,
    deps = [
        ":template",
    ],
)

kt_jvm_library(
    name = "test",
    testonly = True,
    srcs = ["ClosetRuleTest.kt"],
    visibility = PUBLICITY,
    deps = [
        ":factory",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlin_kotlin_test",
    ],
)

kt_jvm_test(
    name = "factory_test",
    srcs = ["ClosetRuleFactoryTest.kt"],
    test_class = "com.jackbradshaw.closet.rule.ClosetRuleFactoryTest",
    deps = [
        ":test",
    ],
)
