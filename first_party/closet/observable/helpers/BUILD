load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("//first_party/closet:publicity.bzl", "PUBLICITY")
load("//first_party/dagger:defs.bzl", "kt_jvm_test_with_dagger")

kt_jvm_library(
    name = "helpers",
    srcs = ["ObservableClosableHelpers.kt"],
    visibility = PUBLICITY,
    deps = [
        "//first_party/closet/observable",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_test_with_dagger(
    name = "observable_closable_helpers_test",
    srcs = ["ObservableClosableHelpersTest.kt"],
    test_class = "com.jackbradshaw.closet.observable.helpers.ObservableClosableHelpersTest",
    deps = [
        ":helpers",
        "//first_party/chronosphere/testingtaskbarrier",
        "//first_party/closet/observable",
        "//first_party/closet/observable/standard",
        "//first_party/closet/observable/standard:component",
        "//first_party/closet/observable/standard:component_impl",
        "//first_party/closet/rule:component",
        "//first_party/closet/rule:component_impl",
        "//first_party/coroutines/io",
        "//first_party/coroutines/testing:coroutines",
        "//first_party/coroutines/testing/realistic:component",
        "//first_party/coroutines/testing/realistic:component_impl",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
        "@com_jackbradshaw_maven//:junit_junit",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlin_kotlin_test",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)
