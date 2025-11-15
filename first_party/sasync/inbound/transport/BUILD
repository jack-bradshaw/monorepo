load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library", "kt_jvm_test")

kt_jvm_library(
    name = "transport",
    srcs = ["InboundTransport.kt"],
    visibility = [
        "//visibility:public",
    ],
    deps = [
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_library(
    name = "impl",
    srcs = ["InboundTransportImpl.kt"],
    visibility = [
        "//visibility:public",
    ],
    deps = [
        ":transport",
        "//:dagger",
        "//first_party/concurrency/pulsar",
        "//first_party/coroutines/io",
        "//first_party/model/frequency:kt_helpers",
        "//first_party/model/frequency:kt_jvm_proto",
        "//first_party/sasync/inbound/config:kt_jvm_proto",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_library(
    name = "module",
    srcs = ["InboundTransportModule.kt"],
    visibility = [
        "//visibility:public",
    ],
    deps = [
        ":impl",
        ":transport",
        "//:dagger",
        "//first_party/sasync/inbound:scope",
    ],
)

kt_jvm_library(
    name = "test",
    srcs = ["InboundTransportTest.kt"],
    visibility = [
        "//visibility:public",
    ],
    deps = [
        ":transport",
        "//first_party/concurrency/pulsar/testing",
        "//first_party/model/count:kt_jvm_proto",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
        "@com_jackbradshaw_maven//:junit_junit",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_test",
    ],
)

kt_jvm_test(
    name = "impl_test",
    timeout = "short",
    testonly = True,
    srcs = ["InboundTransportImplTest.kt"],
    test_class = "com.jackbradshaw.sasync.inbound.transport.InboundTransportImplTest",
    visibility = [
        "//visibility:private",
    ],
    deps = [
        ":impl",
        ":test",
        ":transport",
        "//:dagger",
        "//first_party/concurrency/testing",
        "//first_party/coroutines/testing",
        "//first_party/model/count:kt_jvm_proto",
        "//first_party/model/frequency:kt_jvm_proto",
        "//first_party/sasync/inbound:scope",
        "//first_party/sasync/inbound/config:kt_jvm_proto",
        "@com_jackbradshaw_maven//:javax_inject_javax_inject",
        "@com_jackbradshaw_maven//:junit_junit",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlin_kotlin_test",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)
