load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library", "kt_jvm_test")

kt_jvm_library(
    name = "scope",
    srcs = ["StandardScope.kt"],
    visibility = ["//visibility:public"],
    deps = ["//:dagger"],
)

kt_jvm_library(
    name = "component",
    srcs = ["StandardComponent.kt"],
    visibility = ["//visibility:public"],
    deps = [
        "//first_party/sasync/inbound/transport",
        "//first_party/sasync/outbound/transport",
        "//first_party/sasync/standard/error",
        "//first_party/sasync/standard/input",
        "//first_party/sasync/standard/output",
    ],
)

kt_jvm_library(
    name = "component_impl",
    srcs = ["StandardComponentImpl.kt"],
    visibility = ["//first_party:__subpackages__"],
    deps = [
        ":component",
        ":scope",
        "//:dagger",
        "//first_party/sasync/inbound:component",
        "//first_party/sasync/outbound:component",
        "//first_party/sasync/standard/error:module",
        "//first_party/sasync/standard/input:module",
        "//first_party/sasync/standard/output:module",
    ],
)

kt_jvm_test(
    name = "test",
    srcs = ["StandardTest.kt"],
    test_class = "com.jackbradshaw.sasync.standard.StandardTest",
    visibility = ["//visibility:private"],
    deps = [
        ":component",
        ":component_impl",
        "//first_party/coroutines/testing:component_impl",
        "//first_party/sasync/inbound:component",
        "//first_party/sasync/inbound:component_impl",  # For inboundComponent factory
        "//first_party/sasync/outbound:component",
        "//first_party/sasync/outbound:component_impl",  # For outboundComponent factory
        "@com_jackbradshaw_maven//:com_google_truth_truth",
        "@com_jackbradshaw_maven//:junit_junit",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_test",
    ],
)
