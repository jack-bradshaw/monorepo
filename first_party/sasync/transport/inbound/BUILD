load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library", "kt_jvm_test")

kt_jvm_library(
    name = "inbound",
    srcs = ["Inbound.kt"],
    visibility = ["//first_party/sasync:__subpackages__"],
    deps = [
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_library(
    name = "impl",
    srcs = ["InboundImpl.kt"],
    visibility = ["//first_party/sasync:__subpackages__"],
    deps = [
        ":inbound",
        "//:dagger",
        "//first_party/concurrency/pulsar",
        "//first_party/coroutines/io",
        "//first_party/model/frequency:kt_helpers",
        "//first_party/model/frequency:kt_jvm_proto",
        "//first_party/sasync/transport/inbound/config:kt_jvm_proto",
        "@com_jackbradshaw_maven//:javax_inject_javax_inject",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_library(
    name = "test",
    srcs = ["InboundTest.kt"],
    visibility = ["//first_party/sasync:__subpackages__"],
    deps = [
        ":inbound",
        "//first_party/concurrency/pulsar/testing",
        "//first_party/model/count:kt_jvm_proto",
        "//first_party/model/frequency:kt_jvm_proto",
        "//first_party/sasync/transport/inbound/config:kt_jvm_proto",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
        "@com_jackbradshaw_maven//:javax_inject_javax_inject",
        "@com_jackbradshaw_maven//:junit_junit",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlin_kotlin_test",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_test",
    ],
)

kt_jvm_test(
    name = "impl_test",
    timeout = "short",
    srcs = ["InboundImplTest.kt"],
    test_class = "com.jackbradshaw.sasync.transport.inbound.InboundImplTest",
    visibility = ["//first_party/sasync:__subpackages__"],
    deps = [
        ":impl",
        ":inbound",
        ":test",
        "//:dagger",
        "//first_party/concurrency/testing",
        "//first_party/coroutines/testing",
        "//first_party/model/count:kt_jvm_proto",
        "//first_party/model/frequency:kt_jvm_proto",
        "//first_party/sasync:scope",
        "//first_party/sasync/transport/inbound/config:kt_jvm_proto",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
        "@com_jackbradshaw_maven//:javax_inject_javax_inject",
        "@com_jackbradshaw_maven//:junit_junit",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlin_kotlin_test",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)
