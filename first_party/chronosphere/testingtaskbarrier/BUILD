load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("//first_party/chronosphere:publicity.bzl", "PUBLICITY")
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
    name = "impl",
    srcs = ["TestingTaskBarrierImpl.kt"],
    deps = [
        ":testingtaskbarrier",
    ],
)

kt_jvm_library_with_dagger(
    name = "module",
    srcs = ["TestingTaskBarrierModule.kt"],
    deps = [
        ":impl",
        ":testingtaskbarrier",
    ],
)

kt_jvm_library(
    name = "component",
    srcs = ["TestingTaskBarrierComponent.kt"],
    visibility = PUBLICITY,
    deps = [
        ":testingtaskbarrier",
    ],
)

kt_jvm_library_with_dagger(
    name = "component_impl",
    srcs = ["TestingTaskBarrierComponentImpl.kt"],
    visibility = PUBLICITY,
    deps = [
        ":component",
        ":module",
        ":testingtaskbarrier",
    ],
)

kt_jvm_library(
    name = "test",
    srcs = ["TestingTaskBarrierTest.kt"],
    visibility = PUBLICITY,
    deps = [
        ":testingtaskbarrier",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_test_with_dagger(
    name = "impl_test",
    size = "small",
    srcs = ["TestingTaskBarrierImplTest.kt"],
    test_class = "com.jackbradshaw.chronosphere.testingtaskbarrier.TestingTaskBarrierImplTest",
    deps = [
        ":component",
        ":component_impl",
        ":test",
    ],
)
