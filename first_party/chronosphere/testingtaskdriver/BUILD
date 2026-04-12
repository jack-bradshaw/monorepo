load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("//first_party/chronosphere:publicity.bzl", "PUBLICITY")
load("//first_party/dagger:defs.bzl", "kt_jvm_library_with_dagger", "kt_jvm_test_with_dagger")

kt_jvm_library(
    name = "testingtaskdriver",
    srcs = ["TestingTaskDriver.kt"],
    visibility = PUBLICITY,
    deps = [
        "//first_party/chronosphere/advancable",
    ],
)

kt_jvm_library_with_dagger(
    name = "impl",
    srcs = ["TestingTaskDriverImpl.kt"],
    deps = [
        ":testingtaskdriver",
    ],
)

kt_jvm_library_with_dagger(
    name = "module",
    srcs = ["TestingTaskDriverModule.kt"],
    deps = [
        ":impl",
        ":testingtaskdriver",
    ],
)

kt_jvm_library(
    name = "component",
    srcs = ["TestingTaskDriverComponent.kt"],
    visibility = PUBLICITY,
    deps = [
        ":testingtaskdriver",
    ],
)

kt_jvm_library_with_dagger(
    name = "component_impl",
    srcs = ["TestingTaskDriverComponentImpl.kt"],
    visibility = PUBLICITY,
    deps = [
        ":component",
        ":module",
        ":testingtaskdriver",
    ],
)

kt_jvm_library(
    name = "test",
    srcs = ["TestingTaskDriverTest.kt"],
    visibility = PUBLICITY,
    deps = [
        ":testingtaskdriver",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_test_with_dagger(
    name = "impl_test",
    size = "small",
    srcs = ["TestingTaskDriverImplTest.kt"],
    test_class = "com.jackbradshaw.chronosphere.testingtaskdriver.TestingTaskDriverImplTest",
    deps = [
        ":component",
        ":component_impl",
        ":test",
    ],
)
