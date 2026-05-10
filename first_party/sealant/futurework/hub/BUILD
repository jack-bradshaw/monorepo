load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("//first_party/dagger:defs.bzl", "kt_jvm_library_with_dagger", "kt_jvm_test_with_dagger")

kt_jvm_library(
    name = "hub",
    srcs = ["Hub.kt"],
    visibility = ["//visibility:public"],
    deps = [
        "//first_party/sealant/futurework/connectable",
        "//first_party/sealant/futurework/outlet",
        "//first_party/sealant/futurework/pipe",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_library(
    name = "test_abstract",
    srcs = [
        "HubTest.kt",
    ],
    visibility = ["//visibility:public"],
    deps = [
        ":hub",
        "//first_party/chronosphere/testingtaskbarrier",
        "//first_party/sealant/futurework/connectable:test_abstract",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
        "@com_jackbradshaw_maven//:junit_junit",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)
