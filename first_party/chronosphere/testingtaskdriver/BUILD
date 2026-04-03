load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("//first_party/dagger:defs.bzl", "kt_jvm_library_with_dagger", "kt_jvm_test_with_dagger")
load("//first_party/publicity:publicity.bzl", "PUBLICITY")

kt_jvm_library(
    name = "testingtaskdriver",
    srcs = ["TestingTaskDriver.kt"],
    visibility = PUBLICITY,
    deps = [
        "//first_party/chronosphere/advancable",
    ],
)

kt_jvm_library_with_dagger(
    name = "module",
    srcs = [
        "TestingTaskDriverImpl.kt",
        "TestingTaskDriverModule.kt",
    ],
    visibility = PUBLICITY,
    deps = [
        ":testingtaskdriver",
        "//first_party/chronosphere/advancable",
        "@com_jackbradshaw_maven//:jakarta_inject_jakarta_inject_api",
    ],
)

kt_jvm_library(
    name = "component",
    srcs = ["TestingTaskDriverComponent.kt"],
    visibility = PUBLICITY,
    deps = [
        ":testingtaskdriver",
        "//first_party/chronosphere/advancable",
    ],
)

kt_jvm_library_with_dagger(
    name = "component_impl",
    srcs = ["TestingTaskDriverComponentImpl.kt"],
    visibility = PUBLICITY,
    deps = [
        ":component",
        ":module",
        "//first_party/chronosphere/advancable",
    ],
)

kt_jvm_library(
    name = "test_abstract_testingtaskdriver",
    srcs = ["TestingTaskDriverTest.kt"],
    visibility = ["//first_party:__subpackages__"],
    deps = [
        ":testingtaskdriver",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
        "@com_jackbradshaw_maven//:junit_junit",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_test_with_dagger(
    name = "test_testingtaskdriver",
    size = "small",
    srcs = ["TestingTaskDriverImplTest.kt"],
    test_class = "com.jackbradshaw.chronosphere.testingtaskdriver.TestingTaskDriverImplTest",
    deps = [
        ":module",
        ":test_abstract_testingtaskdriver",
        "//first_party/coroutines/testing/realistic:component",
        "//first_party/coroutines/testing/realistic:component_impl",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
        "@com_jackbradshaw_maven//:junit_junit",
    ],
)
