load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library", "kt_jvm_test")
load("//first_party/dagger:defs.bzl", "kt_jvm_library_with_dagger", "kt_jvm_test_with_dagger")
load("//first_party/sasync:publicity.bzl", "PUBLICITY")

kt_jvm_library(
    name = "transport",
    srcs = ["OutboundTransport.kt"],
    visibility = PUBLICITY,
    deps = ["@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core"],
)

kt_jvm_library_with_dagger(
    name = "impl",
    srcs = ["OutboundTransportImpl.kt"],
    deps = [
        ":transport",
        "//first_party/coroutines/io",
        "//first_party/sasync/outbound/config:kt_jvm_proto",
        "//first_party/universal/count:kt_jvm_proto",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_library_with_dagger(
    name = "module",
    srcs = ["OutboundTransportModule.kt"],
    visibility = ["//first_party/sasync/outbound:__subpackages__"],
    deps = [
        "impl",
        ":transport",
        "//first_party/sasync/outbound:scope",
    ],
)

kt_jvm_library(
    name = "test",
    srcs = ["OutboundTransportTest.kt"],
    visibility = PUBLICITY,
    deps = [
        ":impl",
        ":transport",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
        "@com_jackbradshaw_maven//:junit_junit",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlin_kotlin_test",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_test",
    ],
)

kt_jvm_test_with_dagger(
    name = "impl_test",
    testonly = True,
    srcs = ["OutboundTransportImplTest.kt"],
    test_class = "com.jackbradshaw.sasync.outbound.transport.OutboundTransportImplTest",
    deps = [
        ":impl",
        ":test",
        ":transport",
        "//first_party/coroutines/testing",
        "//first_party/sasync/outbound:scope",
        "//first_party/sasync/outbound/config:kt_jvm_proto",
        "//first_party/universal/count:kt_jvm_proto",
        "@com_jackbradshaw_maven//:junit_junit",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)
