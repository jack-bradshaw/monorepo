load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_binary", "kt_jvm_library", "kt_jvm_test")
load("//first_party/publicity:publicity.bzl", "PUBLICITY")

kt_jvm_library(
    name = "entrypoint",
    srcs = ["EntryPoint.kt"],
    visibility = ["//first_party/publicity/conformance:__subpackages__"],
    deps = [
        "//first_party/concurrency:component",
        "//first_party/coroutines:component",
        "//first_party/publicity/conformance/model",
        "//first_party/publicity/conformance/runner:component",
        "//first_party/publicity/conformance/runner:component_impl",
        "//first_party/sasync/inbound:component",
        "//first_party/sasync/inbound:component_impl",
        "//first_party/sasync/inbound/config:default",
        "//first_party/sasync/outbound:component",
        "//first_party/sasync/outbound:component_impl",
        "//first_party/sasync/outbound/config:default",
        "//first_party/sasync/standard:component",
        "//first_party/sasync/standard:component_impl",
    ],
)

kt_jvm_test(
    name = "test",
    srcs = ["EntryPointTest.kt"],
    test_class = "com.jackbradshaw.publicity.conformance.entrypoint.EntryPointTest",
    deps = [
        ":entrypoint",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
        "@com_jackbradshaw_maven//:junit_junit",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlin_kotlin_test",
    ],
)
