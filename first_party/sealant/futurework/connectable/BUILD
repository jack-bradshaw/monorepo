load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("//first_party/dagger:defs.bzl", "kt_jvm_library_with_dagger", "kt_jvm_test_with_dagger")

kt_jvm_library(
    name = "connectable",
    srcs = ["Connectable.kt"],
    visibility = ["//visibility:public"],
    deps = [
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core", "//first_party/closet/observable"
    ],
)

kt_jvm_library(
    name = "test_abstract",
    srcs = ["ConnectableTest.kt"],
    visibility = ["//visibility:public"],
    deps = [
        ":connectable", "//first_party/chronosphere/testingtaskbarrier", "@com_jackbradshaw_maven//:com_google_truth_truth", "@com_jackbradshaw_maven//:junit_junit", "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core"
    ],
)
