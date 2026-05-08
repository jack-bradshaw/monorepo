load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("//first_party/concurrency/quinn:publicity.bzl", "PUBLICITY")
load("//first_party/dagger:defs.bzl", "kt_jvm_library_with_dagger", "kt_jvm_test_with_dagger")

kt_jvm_library(
    name = "hub",
    srcs = ["IdleableQuinnHub.kt"],
    visibility = PUBLICITY,
    deps = [
        "//first_party/chronosphere/idleable",
        "//first_party/concurrency/quinn",
    ],
)

kt_jvm_library_with_dagger(
    name = "impl",
    srcs = ["IdleableQuinnHubImpl.kt"],
    deps = [
        ":hub",
        "//first_party/concurrency/quinn",
        "//first_party/concurrency/quinn:scope",
        "//first_party/concurrency/quinn/testing/idleable",
        "//first_party/concurrency/quinn/testing/idleable:impl",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_library_with_dagger(
    name = "module",
    srcs = ["IdleableQuinnHubModule.kt"],
    visibility = [
        "//first_party/concurrency/quinn/testing:__subpackages__",
    ],
    deps = [
        ":hub",
        ":impl",
        "//first_party/concurrency/quinn",
    ],
)

kt_jvm_library(
    name = "test",
    srcs = ["IdleableQuinnHubTest.kt"],
    visibility = PUBLICITY,
    deps = [
        ":hub",
        "//first_party/chronosphere/testingtaskbarrier",
        "//first_party/concurrency/quinn",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
        "@com_jackbradshaw_maven//:junit_junit",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_test_with_dagger(
    name = "impl_test",
    srcs = ["IdleableQuinnHubImplTest.kt"],
    test_class = "com.jackbradshaw.concurrency.quinn.testing.hub.IdleableQuinnHubImplTest",
    deps = [
        ":hub",
        ":module",
        ":test",
        "//first_party/chronosphere/testingtaskbarrier",
        "//first_party/chronosphere/testingtaskbarrier:component",
        "//first_party/chronosphere/testingtaskbarrier:component_impl",
        "//first_party/concurrency/quinn:scope",
        "//first_party/concurrency/quinn/testing/prod:component",
        "//first_party/concurrency/quinn/testing/prod:component_impl",
        "//first_party/coroutines/testing/realistic:component",
        "//first_party/coroutines/testing/realistic:component_impl",
        "@com_jackbradshaw_maven//:junit_junit",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)
