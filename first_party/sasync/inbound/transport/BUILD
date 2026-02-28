load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("//first_party/dagger:defs.bzl", "kt_jvm_library_with_dagger", "kt_jvm_test_with_dagger")
load("//first_party/sasync:publicity.bzl", "PUBLICITY")

kt_jvm_library(
    name = "transport",
    srcs = ["InboundTransport.kt"],
    visibility = PUBLICITY,
    deps = ["@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core"],
)

kt_jvm_library_with_dagger(
    name = "impl",
    srcs = ["InboundTransportImpl.kt"],
    deps = [
        ":transport",
        "//first_party/concurrency/pulsar",
        "//first_party/coroutines/io",
        "//first_party/sasync/inbound/config:kt_jvm_proto",
        "//first_party/universal/frequency:kt_helpers",
        "//first_party/universal/frequency:kt_jvm_proto",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_library_with_dagger(
    name = "module",
    srcs = ["InboundTransportModule.kt"],
    visibility = ["//first_party/sasync/inbound:__subpackages__"],
    deps = [
        ":impl",
        ":transport",
        "//first_party/sasync/inbound:scope",
    ],
)

kt_jvm_library(
    name = "test",
    srcs = ["InboundTransportTest.kt"],
    visibility = PUBLICITY,
    deps = [
        ":transport",
        "//first_party/concurrency/pulsar/testing",
        "//first_party/universal/count:kt_jvm_proto",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
        "@com_jackbradshaw_maven//:junit_junit",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_test",
    ],
)

kt_jvm_test_with_dagger(
    name = "impl_test",
    timeout = "short",
    testonly = True,
    srcs = ["InboundTransportImplTest.kt"],
    test_class = "com.jackbradshaw.sasync.inbound.transport.InboundTransportImplTest",
    deps = [
        ":impl",
        ":test",
        ":transport",
        "//first_party/concurrency/testing:component_impl",
        "//first_party/coroutines/testing:component_impl",
        "//first_party/sasync/inbound:scope",
        "//first_party/sasync/inbound/config:kt_jvm_proto",
        "//first_party/universal/count:kt_jvm_proto",
        "//first_party/universal/frequency:kt_jvm_proto",
        "@com_jackbradshaw_maven//:junit_junit",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlin_kotlin_test",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)
