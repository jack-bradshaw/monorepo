load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library", "kt_jvm_test")

kt_jvm_library(
    name = "outbound",
    srcs = ["Outbound.kt"],
    visibility = ["//first_party/sasync:__subpackages__"],
    deps = [
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_library(
    name = "impl",
    srcs = ["OutboundImpl.kt"],
    visibility = ["//first_party/sasync:__subpackages__"],
    deps = [
        ":outbound",
        "//:dagger",
        "//first_party/autofactory",
        "//first_party/coroutines/io",
        "//first_party/model/count:kt_jvm_proto",
        "//first_party/sasync/transport/outbound/config:kt_jvm_proto",
        "@com_jackbradshaw_maven//:javax_inject_javax_inject",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_library(
    name = "test",
    srcs = ["OutboundTest.kt"],
    visibility = ["//first_party/sasync:__subpackages__"],
    deps = [
        ":outbound",
        "//first_party/model/count:kt_jvm_proto",
        "//first_party/sasync/transport/outbound/config:kt_jvm_proto",
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
    srcs = ["OutboundImplTest.kt"],
    test_class = "com.jackbradshaw.sasync.transport.outbound.OutboundImplTest",
    visibility = ["//first_party/sasync:__subpackages__"],
    deps = [
        ":impl",
        ":outbound",
        ":test",
        "//:dagger",
        "//first_party/coroutines/testing",
        "//first_party/model/count:kt_jvm_proto",
        "//first_party/sasync:scope",
        "//first_party/sasync/transport/outbound/config:kt_jvm_proto",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
        "@com_jackbradshaw_maven//:javax_inject_javax_inject",
        "@com_jackbradshaw_maven//:junit_junit",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlin_kotlin_test",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)
