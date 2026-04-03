load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library", "kt_jvm_test")

package(default_visibility = ["//visibility:public"])

kt_jvm_library(
    name = "rule",
    srcs = [
        "ClosetRule.kt",
        "ClosetRuleFactory.kt",
        "ClosetRuleTemplate.kt",
    ],
    deps = [
        "@com_jackbradshaw_maven//:junit_junit",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_library(
    name = "test_abstract",
    testonly = True,
    srcs = ["ClosetRuleTest.kt"],
    deps = [
        ":rule",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
        "@com_jackbradshaw_maven//:junit_junit",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlin_kotlin_test",
    ],
)

kt_jvm_library(
    name = "factory_test",
    testonly = True,
    srcs = ["ClosetRuleFactoryTest.kt"],
    deps = [
        ":rule",
        ":test_abstract",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
        "@com_jackbradshaw_maven//:junit_junit",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlin_kotlin_test",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_test(
    name = "test",
    test_class = "com.jackbradshaw.closet.rule.ClosetRuleFactoryTest",
    runtime_deps = [":factory_test"],
)
