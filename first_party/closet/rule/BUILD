load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library", "kt_jvm_test")
load("//first_party/closet:publicity.bzl", "PUBLICITY")
load("//first_party/dagger:defs.bzl", "kt_jvm_library_with_dagger")

kt_jvm_library(
    name = "rule",
    srcs = ["AutoCloseRule.kt"],
    visibility = PUBLICITY,
    deps = [
        "//first_party/closet/suspending",
        "@com_jackbradshaw_maven//:junit_junit",
    ],
)

kt_jvm_library_with_dagger(
    name = "impl",
    srcs = ["AutoCloseRuleImpl.kt"],
    deps = [
        ":rule",
        "//first_party/closet:scope",
        "//first_party/closet/suspending",
        "@com_jackbradshaw_maven//:junit_junit",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_library_with_dagger(
    name = "module",
    srcs = ["AutoCloseRuleModule.kt"],
    deps = [
        ":impl",
        ":rule",
    ],
)

kt_jvm_library(
    name = "component",
    srcs = ["AutoCloseRuleComponent.kt"],
    visibility = PUBLICITY,
    deps = [
        ":rule",
    ],
)

kt_jvm_library_with_dagger(
    name = "component_impl",
    srcs = ["AutoCloseRuleComponentImpl.kt"],
    visibility = PUBLICITY,
    deps = [
        ":component",
        ":module",
        ":rule",
        "//first_party/closet:scope",
    ],
)

kt_jvm_library(
    name = "test",
    testonly = True,
    srcs = ["AutoCloseRuleTest.kt"],
    visibility = PUBLICITY,
    deps = [
        ":rule",
        "//first_party/closet/suspending",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
        "@com_jackbradshaw_maven//:junit_junit",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlin_kotlin_test",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_test(
    name = "impl_test",
    srcs = ["AutoCloseRuleImplTest.kt"],
    test_class = "com.jackbradshaw.closet.rule.AutoCloseRuleImplTest",
    deps = [
        ":impl",
        ":test",
        "//first_party/coroutines/testing/realistic:component_impl",
    ],
)
