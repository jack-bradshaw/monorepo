load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")

kt_jvm_library(
    name = "observable",
    srcs = [
        "ObservableClosable.kt",
        "ObservableClosableTest.kt",
    ],
    visibility = ["//visibility:public"],
    deps = [
        "@com_jackbradshaw_maven//:com_google_truth_truth",
        "@com_jackbradshaw_maven//:junit_junit",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)
