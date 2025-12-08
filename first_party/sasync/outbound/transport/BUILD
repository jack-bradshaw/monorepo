load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library", "kt_jvm_test")

kt_jvm_library(
    name = "transport",
    srcs = ["OutboundTransport.kt"],
    visibility = [
        "//visibility:public",
    ],
    deps = [
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_library(
    name = "impl",
    srcs = ["OutboundTransportImpl.kt"],
    visibility = [
        "//visibility:public",
    ],
    deps = [
        ":transport",
        "//:dagger",
        "//first_party/coroutines/io",
        "//first_party/sasync/outbound/config:kt_jvm_proto",
        "//first_party/universal/count:kt_jvm_proto",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_library(
    name = "module",
    srcs = ["OutboundTransportModule.kt"],
    visibility = [
        "//visibility:public",
    ],
    deps = [
        "impl",
        ":transport",
        "//:dagger",
        "//first_party/sasync/outbound:scope",
    ],
)

kt_jvm_library(
    name = "test",
    srcs = ["OutboundTransportTest.kt"],
    visibility = [
        "//visibility:public",
    ],
    deps = [
        ":impl",
        ":transport",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
        "@com_jackbradshaw_maven//:junit_junit",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_test",
    ],
)

kt_jvm_test(
    name = "impl_test",
    testonly = True,
    srcs = ["OutboundTransportImplTest.kt"],
    test_class = "com.jackbradshaw.sasync.outbound.transport.OutboundTransportImplTest",
    visibility = [
        "//visibility:private",
    ],
    deps = [
        ":impl",
        ":test",
        ":transport",
        "//:dagger",
        "//first_party/coroutines/testing",
        "//first_party/sasync/outbound:scope",
        "//first_party/sasync/outbound/config:kt_jvm_proto",
        "//first_party/universal/count:kt_jvm_proto",
        "@com_jackbradshaw_maven//:javax_inject_javax_inject",
        "@com_jackbradshaw_maven//:junit_junit",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)
