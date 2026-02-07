load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library", "kt_jvm_test")
load("//first_party/dagger:defs.bzl", "kt_jvm_library_with_dagger")
load("//first_party/publicity:defs.bzl", "public")

PUBLICITY = public()

kt_jvm_library_with_dagger(
    name = "scope",
    srcs = ["StandardScope.kt"],
    visibility = PUBLICITY,
)

kt_jvm_library(
    name = "component",
    srcs = ["StandardComponent.kt"],
    visibility = PUBLICITY,
    deps = [
        "//first_party/sasync/inbound/transport",
        "//first_party/sasync/outbound/transport",
        "//first_party/sasync/standard/error",
        "//first_party/sasync/standard/input",
        "//first_party/sasync/standard/output",
    ],
)

kt_jvm_library_with_dagger(
    name = "component_impl",
    srcs = ["StandardComponentImpl.kt"],
    visibility = PUBLICITY,
    deps = [
        ":component",
        ":scope",
        "//first_party/sasync/inbound:component",
        "//first_party/sasync/inbound:component_impl",
        "//first_party/sasync/inbound/config:default",
        "//first_party/sasync/outbound:component",
        "//first_party/sasync/outbound:component_impl",
        "//first_party/sasync/outbound/config:default",
        "//first_party/sasync/standard/error:module",
        "//first_party/sasync/standard/input:module",
        "//first_party/sasync/standard/output:module",
    ],
)

kt_jvm_test(
    name = "test",
    srcs = ["StandardTest.kt"],
    test_class = "com.jackbradshaw.sasync.standard.StandardTest",
    deps = [
        ":component",
        ":component_impl",
        "//first_party/coroutines/testing:component_impl",
        "//first_party/sasync/inbound:component",
        "//first_party/sasync/inbound:component_impl",
        "//first_party/sasync/outbound:component",
        "//first_party/sasync/outbound:component_impl",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
        "@com_jackbradshaw_maven//:junit_junit",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_test",
    ],
)
