load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("//first_party/dagger:defs.bzl", "kt_jvm_library_with_dagger", "kt_jvm_test_with_dagger")
load("//first_party/publicity:publicity.bzl", "PUBLICITY")

kt_jvm_library(
    name = "runner",
    srcs = ["Runner.kt"],
    visibility = PUBLICITY,
)

kt_jvm_library(
    name = "component",
    srcs = ["RunnerComponent.kt"],
    visibility = PUBLICITY,
    deps = [
        ":runner",
    ],
)

kt_jvm_library_with_dagger(
    name = "component_impl",
    srcs = ["RunnerComponentImpl.kt"],
    visibility = PUBLICITY,
    deps = [
        ":component",
        ":module",
        ":runner",
        "//first_party/publicity/conformance/model",
        "//first_party/publicity/conformance/packagechecker:impl_module",
        "//first_party/publicity/conformance/workspacechecker:impl_module",
        "//first_party/sasync/standard:component",
        "//first_party/sasync/standard:component_impl",
    ],
)

kt_jvm_library_with_dagger(
    name = "impl",
    srcs = ["RunnerImpl.kt"],
    visibility = ["//first_party/publicity/conformance:__subpackages__"],
    deps = [
        ":runner",
        "//first_party/publicity/conformance/model",
        "//first_party/publicity/conformance/workspacechecker:checker",
        "//first_party/sasync/outbound/transport",
        "//first_party/sasync/standard/error",
        "//first_party/sasync/standard/output",
    ],
)

kt_jvm_library_with_dagger(
    name = "module",
    srcs = ["RunnerModule.kt"],
    visibility = ["//first_party/publicity/conformance:__subpackages__"],
    deps = [
        ":impl",
        ":runner",
    ],
)

kt_jvm_library(
    name = "test",
    srcs = ["RunnerTest.kt"],
    visibility = ["//first_party/publicity/conformance:__subpackages__"],
    deps = [
        ":runner",
        "//first_party/publicity/conformance/model",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
        "@com_jackbradshaw_maven//:junit_junit",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_test_with_dagger(
    name = "impl_test",
    # timeout = "short" is default for unit tests usually, but good to be explicit if it was there.
    timeout = "short",
    srcs = ["RunnerImplTest.kt"],
    test_class = "com.jackbradshaw.publicity.conformance.runner.RunnerImplTest",
    deps = [
        ":impl",
        ":module",
        ":runner",
        ":test",
        "//first_party/concurrency/testing:component",
        "//first_party/concurrency/testing:component_impl",
        "//first_party/coroutines/testing:component",
        "//first_party/coroutines/testing:component_impl",
        "//first_party/publicity/conformance/model",
        "//first_party/publicity/conformance/packagechecker:impl_module",
        "//first_party/publicity/conformance/workspacechecker:checker",
        "//first_party/publicity/conformance/workspacechecker:impl_module",
        "//first_party/sasync/inbound:component",
        "//first_party/sasync/inbound:component_impl",
        "//first_party/sasync/inbound/config:default",
        "//first_party/sasync/outbound:component",
        "//first_party/sasync/outbound:component_impl",
        "//first_party/sasync/outbound/config:default",
        "//first_party/sasync/outbound/transport",
        "//first_party/sasync/standard:component",
        "//first_party/sasync/standard:component_impl",
        "@com_jackbradshaw_maven//:junit_junit",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_test",
    ],
)
