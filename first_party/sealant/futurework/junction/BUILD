load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("//first_party/dagger:defs.bzl", "kt_jvm_library_with_dagger", "kt_jvm_test_with_dagger")

kt_jvm_library(
    name = "junction",
    srcs = ["Junction.kt"],
    visibility = ["//visibility:public"],
    deps = [
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core", "//first_party/sealant/futurework/connectable"
    ],
)

kt_jvm_library(
    name = "test_abstract",
    srcs = ["JunctionTest.kt"],
    visibility = ["//visibility:public"],
    deps = [
        ":junction", "//first_party/chronosphere/testingtaskbarrier", "@com_jackbradshaw_maven//:com_google_truth_truth", "@com_jackbradshaw_maven//:junit_junit", "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core", "//first_party/sealant/futurework/connectable:test_abstract"
    ],
)
