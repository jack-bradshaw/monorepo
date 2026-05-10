load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")

kt_jvm_library(
    name = "flow",
    srcs = [
        "SealedFlow.kt",
        "SealedFlowImpl.kt",
    ],
    visibility = ["//visibility:public"],
    deps = [
        "//first_party/closet/observable",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_library(
    name = "test_abstract",
    srcs = ["SealedFlowTest.kt"],
    visibility = ["//visibility:public"],
    deps = [
        ":flow",
        "//first_party/chronosphere/testingtaskbarrier",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
        "@com_jackbradshaw_maven//:junit_junit",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)
