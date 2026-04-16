load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("//first_party/dagger:defs.bzl", "kt_jvm_library_with_dagger", "kt_jvm_test_with_dagger")

kt_jvm_library(
    name = "entrypoint",
    srcs = ["EntryPoint.kt"],
    visibility = ["//visibility:public"],
    deps = [
        "@com_jackbradshaw_maven//:com_google_devtools_ksp_symbol_processing_api",
    ],
)

kt_jvm_library_with_dagger(
    name = "entrypoint_impl",
    srcs = ["EntryPointImpl.kt"],
    visibility = ["//visibility:public"],
    deps = [
        ":entrypoint",
        "//first_party/coroutines",
        "//first_party/coroutines/io",
        "//first_party/oksp/application",
        "//first_party/oksp/application:component",
        "//first_party/oksp/application:impl",
        "//first_party/oksp/processor",
        "//first_party/oksp/processor:processor_impl",
        "//first_party/oksp/service",
        "@com_jackbradshaw_maven//:com_google_devtools_ksp_symbol_processing_api",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_test",
    ],
)

kt_jvm_library(
    name = "entrypoint_impl_declared",
    resource_strip_prefix = "first_party/oksp/entrypoint/resources",
    resources = glob(["resources/**"]),
    visibility = ["//visibility:public"],
    runtime_deps = [
        ":entrypoint_impl",
    ],
)

kt_jvm_library_with_dagger(
    name = "entrypoint_test_lib",
    srcs = ["EntryPointTest.kt"],
    deps = [
        ":entrypoint",
        "//first_party/coroutines",
        "//first_party/coroutines/testing",
        "//first_party/kale/ksprunner",
        "//first_party/kale/ksprunner:component",
        "//first_party/kale/ksprunner:component_impl",
        "//first_party/kale/provider:chassis",
        "//first_party/kale/provider:component",
        "//first_party/kale/provider:component_impl",
        "//first_party/oksp/application",
        "//first_party/oksp/processor",
        "@com_jackbradshaw_maven//:com_google_devtools_ksp_symbol_processing_api",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
        "@com_jackbradshaw_maven//:junit_junit",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_test",
    ],
)

kt_jvm_test_with_dagger(
    name = "entrypoint_impl_test",
    srcs = ["EntryPointImplTest.kt"],
    test_class = "com.jackbradshaw.oksp.entrypoint.EntryPointImplTest",
    deps = [
        ":entrypoint_impl",
        ":entrypoint_test_lib",
        "//first_party/oksp/application",
        "//first_party/oksp/application:component",
        "//first_party/oksp/application:impl",
        "//first_party/oksp/application/testing",
        "//first_party/oksp/processor:processor_impl",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_test",
    ],
)
