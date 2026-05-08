load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("//first_party/concurrency:publicity.bzl", "PUBLICITY")
load("//first_party/dagger:defs.bzl", "kt_jvm_library_with_dagger", "kt_jvm_test_with_dagger")

kt_jvm_library(
    name = "pulsar",
    srcs = ["Pulsar.kt"],
    visibility = PUBLICITY,
    deps = ["@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core"],
)

kt_jvm_library_with_dagger(
    name = "impl",
    srcs = ["PulsarImpl.kt"],
    deps = [
        ":pulsar",
        "//first_party/concurrency/pulsar:scope",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_library_with_dagger(
    name = "module",
    srcs = ["PulsarModule.kt"],
    visibility = ["//first_party/concurrency:__pkg__"],
    deps = [
        ":impl",
        ":pulsar",
    ],
)

kt_jvm_library(
    name = "test",
    testonly = True,
    srcs = ["PulsarTest.kt"],
    visibility = PUBLICITY,
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
    deps = [
        ":impl",
        ":pulsar",
        ":test",
        "//first_party/concurrency/pulsar:scope",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
        "@com_jackbradshaw_maven//:junit_junit",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlin_kotlin_test",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_library_with_dagger(
    name = "scope",
    srcs = ["PulsarScope.kt"],
    visibility = ["//first_party/concurrency:__subpackages__"],
)

kt_jvm_library(
    name = "component",
    srcs = ["PulsarComponent.kt"],
    visibility = PUBLICITY,
    deps = ["//first_party/concurrency/pulsar"],
)

kt_jvm_library_with_dagger(
    name = "component_impl",
    srcs = ["PulsarComponentImpl.kt"],
    visibility = PUBLICITY,
    deps = [
        ":component",
        ":scope",
        "//first_party/concurrency/pulsar:module",
    ],
)
