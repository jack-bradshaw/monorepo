load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("//first_party/dagger:defs.bzl", "kt_jvm_library_with_dagger", "kt_jvm_test_with_dagger")

kt_jvm_library(
    name = "pulsar",
    srcs = ["Pulsar.kt"],
    visibility = ["//visibility:public"],
    deps = [
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_library_with_dagger(
    name = "impl",
    srcs = ["PulsarImpl.kt"],
    visibility = ["//visibility:public"],
    deps = [
        ":pulsar",
        "//first_party/concurrency:scope",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_library_with_dagger(
    name = "module",
    srcs = ["PulsarModule.kt"],
    visibility = ["//visibility:public"],
    deps = [
        ":impl",
        ":pulsar",
    ],
)

kt_jvm_library(
    name = "test",
    testonly = True,
    srcs = ["PulsarTest.kt"],
    visibility = ["//visibility:public"],
    deps = [
        ":pulsar",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
        "@com_jackbradshaw_maven//:junit_junit",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlin_kotlin_test",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_test",
    ],
)

kt_jvm_test_with_dagger(
    name = "impl_test",
    srcs = ["PulsarImplTest.kt"],
    test_class = "com.jackbradshaw.concurrency.pulsar.PulsarImplTest",
    visibility = ["//visibility:private"],
    deps = [
        ":impl",
        ":pulsar",
        ":test",
        "//first_party/concurrency:scope",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
        "@com_jackbradshaw_maven//:junit_junit",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlin_kotlin_test",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)
