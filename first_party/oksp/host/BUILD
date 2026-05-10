load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("//first_party/dagger:defs.bzl", "kt_jvm_library_with_dagger", "kt_jvm_test_with_dagger")

kt_jvm_library(
    name = "host",
    srcs = ["Host.kt"],
    visibility = ["//visibility:public"],
    deps = [
        "@com_jackbradshaw_maven//:com_google_devtools_ksp_symbol_processing_api",
    ],
)

kt_jvm_library_with_dagger(
    name = "host_impl",
    srcs = [
        "HostImpl.kt",
    ],
    visibility = ["//visibility:public"],
    deps = [
        ":host",
        "//first_party/concurrency/quinn:component",
        "//first_party/concurrency/quinn:component_impl",
        "//first_party/coroutines:component",
        "//first_party/coroutines:component_impl",
        "//first_party/coroutines/io",
        "//first_party/oksp/application",
        "//first_party/oksp/application:component",
        "//first_party/oksp/application:impl",
        "//first_party/oksp/model:ksp_context",
        "//first_party/oksp/model:log_level",
        "//first_party/oksp/model:resource",
        "//first_party/oksp/service",
        "//first_party/sasync/inbound:component",
        "//first_party/sasync/inbound:component_impl",
        "//first_party/sealant:component",
        "//first_party/sealant:component_impl",
        "//first_party/sealant/flow",
        "//first_party/sasync/inbound/config:default",
        "//first_party/sasync/inbound/config:kt_jvm_proto",
        "//first_party/sasync/outbound:component",
        "//first_party/sasync/outbound:component_impl",
        "//first_party/sasync/outbound/config:default",
        "//first_party/sasync/outbound/config:kt_jvm_proto",
        "//first_party/sasync/outbound/transport",
        "//first_party/sasync/standard:component",
        "//first_party/sasync/standard:component_impl",
        "//first_party/sasync/standard/error",
        "@com_jackbradshaw_maven//:com_google_devtools_ksp_symbol_processing_api",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_test",
    ],
)

kt_jvm_library(
    name = "host_impl_declared",
    resource_strip_prefix = "first_party/oksp/host/resources",
    resources = glob(["resources/**"]),
    visibility = ["//visibility:public"],
    runtime_deps = [
        ":host_impl",
    ],
)

kt_jvm_library_with_dagger(
    name = "host_test_lib",
    srcs = ["HostTest.kt"],
    deps = [
        ":host",
        "//first_party/chronosphere/testingtaskbarrier",
        "//first_party/coroutines:component",
        "//first_party/coroutines/testing:coroutines",
        "//first_party/coroutines/testing/realistic:component",
        "//first_party/coroutines/testing/realistic:component_impl",
        "//first_party/kale/provider:component",
        "//first_party/kale/provider:component_impl",
        "//first_party/kale/provider:runner",
        "//first_party/oksp/application",
        "@com_jackbradshaw_maven//:com_google_devtools_ksp_symbol_processing_api",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
        "@com_jackbradshaw_maven//:junit_junit",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_test",
    ],
)

kt_jvm_test_with_dagger(
    name = "host_impl_test",
    srcs = ["HostImplTest.kt"],
    shard_count = 2,
    test_class = "com.jackbradshaw.oksp.host.HostImplTest",
    deps = [
        ":host_impl",
        ":host_test_lib",
        "//first_party/oksp/application",
        "//first_party/oksp/application:component",
        "//first_party/oksp/application:impl",
        "//first_party/oksp/application/passed",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_test",
    ],
)

kt_jvm_test_with_dagger(
    name = "host_ksp_service_test",
    srcs = ["HostKspServiceTest.kt"],
    test_class = "com.jackbradshaw.oksp.host.HostKspServiceTest",
    deps = [
        ":host_impl",
        "//first_party/chronosphere/testingtaskbarrier:component_impl",
        "//first_party/concurrency/quinn:component_impl",
        "//first_party/concurrency/quinn/testing:idleable",
        "//first_party/coroutines/testing/realistic:component_impl",
        "//first_party/kale/model:log",
        "//first_party/kale/model:result",
        "//first_party/kale/model:source",
        "//first_party/kale/provider:component_impl",
        "//first_party/kale/provider:runner",
        "//first_party/oksp/application:adapter",
        "//first_party/oksp/application/passed",
        "//first_party/oksp/service",
        "//first_party/oksp/service:test_lib",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_test",
    ],
)
