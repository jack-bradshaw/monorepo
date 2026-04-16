load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library", "kt_jvm_test")
load("//first_party/dagger:defs.bzl", "kt_jvm_library_with_dagger")
load("//first_party/kale:publicity.bzl", "PUBLICITY")

kt_jvm_library(
    name = "rule",
    srcs = ["ResolverTestRule.kt"],
    visibility = PUBLICITY,
    deps = [
        "//first_party/closet/rule:template",
        "//first_party/kale/resolver/chassis",
        "//first_party/kale/resolver/chassis:component_impl",
    ],
)

kt_jvm_test(
    name = "test",
    srcs = ["ResolverTestRuleTest.kt"],
    test_class = "com.jackbradshaw.kale.resolver.rule.ResolverTestRuleTest",
    deps = [
        ":rule",
        "//first_party/closet/rule",
        "//first_party/closet/rule:test",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
        "@com_jackbradshaw_maven//:junit_junit",
    ],
)
