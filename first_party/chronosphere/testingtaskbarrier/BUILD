load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("//first_party/coroutines:publicity.bzl", "PUBLICITY")
load("//first_party/dagger:defs.bzl", "kt_jvm_library_with_dagger", "kt_jvm_test_with_dagger")

kt_jvm_library(
    name = "testingtaskbarrier",
    srcs = ["TestingTaskBarrier.kt"],
    visibility = PUBLICITY,
    deps = [
        "//first_party/chronosphere/idleable",
    ],
)

kt_jvm_library_with_dagger(
    name = "module",
    srcs = [
        "TestingTaskBarrierImpl.kt",
        "TestingTaskBarrierModule.kt",
    ],
    visibility = PUBLICITY,
    deps = [
        ":testingtaskbarrier",
        "//first_party/chronosphere/idleable",
        "@com_jackbradshaw_maven//:jakarta_inject_jakarta_inject_api",
    ],
)

kt_jvm_library(
    name = "component",
    srcs = ["TestingTaskBarrierComponent.kt"],
    visibility = PUBLICITY,
    deps = [
        ":testingtaskbarrier",
        "//first_party/chronosphere/idleable",
    ],
)

kt_jvm_library_with_dagger(
    name = "component_impl",
    srcs = ["TestingTaskBarrierComponentImpl.kt"],
    visibility = PUBLICITY,
    deps = [
        ":component",
        ":module",
        "//first_party/chronosphere/idleable",
    ],
)

kt_jvm_library(
    name = "test_abstract_testingtaskbarrier",
    srcs = ["TestingTaskBarrierTest.kt"],
    visibility = ["//first_party:__subpackages__"],
    deps = [
        ":testingtaskbarrier",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
        "@com_jackbradshaw_maven//:junit_junit",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_test_with_dagger(
    name = "test_testingtaskbarrier",
    size = "small",
    srcs = ["TestingTaskBarrierImplTest.kt"],
    test_class = "com.jackbradshaw.chronosphere.testingtaskbarrier.TestingTaskBarrierImplTest",
    deps = [
        ":module",
        ":test_abstract_testingtaskbarrier",
        "//first_party/coroutines/testing/realistic:component",
        "//first_party/coroutines/testing/realistic:component_impl",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
        "@com_jackbradshaw_maven//:junit_junit",
    ],
)
