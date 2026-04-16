load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library", "kt_jvm_test")
load("//first_party/dagger:defs.bzl", "kt_jvm_library_with_dagger", "kt_jvm_test_with_dagger")
load("//first_party/kale:publicity.bzl", "PUBLICITY")

kt_jvm_library(
    name = "runner",
    srcs = ["ProcessorRunner.kt"],
    visibility = PUBLICITY,
    deps = [
        "//first_party/kale/model:artifacts",
        "//first_party/kale/model:log",
        "//first_party/kale/model:result",
        "//first_party/kale/model:source",
        "//first_party/kale/model:versions",
        "@com_jackbradshaw_maven//:com_google_devtools_ksp_symbol_processing_api",
    ],
)

kt_jvm_library_with_dagger(
    name = "impl",
    srcs = [
        "ProcessorRunnerImpl.kt",
    ],
    deps = [
        ":runner",
        "//first_party/kale/model:result",
        "//first_party/kale/model:source",
        "//first_party/kale/model:versions",
        "//first_party/kale/provider:runner",
        "@com_jackbradshaw_maven//:com_google_devtools_ksp_symbol_processing_api",
    ],
)

kt_jvm_library_with_dagger(
    name = "module",
    srcs = [
        "ProcessorRunnerModule.kt",
    ],
    deps = [
        ":impl",
        ":runner",
    ],
)

kt_jvm_library(
    name = "component",
    srcs = ["ProcessorRunnerComponent.kt"],
    visibility = PUBLICITY,
    deps = [
        ":runner",
    ],
)

kt_jvm_library_with_dagger(
    name = "component_impl",
    srcs = ["ProcessorRunnerComponentImpl.kt"],
    visibility = PUBLICITY,
    deps = [
        ":component",
        ":module",
        "//first_party/kale:scope",
        "//first_party/kale/provider:component",
        "//first_party/kale/provider:component_impl",
    ],
)

kt_jvm_library(
    name = "test",
    testonly = True,
    srcs = [
        "ProcessorRunnerTest.kt",
    ],
    visibility = PUBLICITY,
    deps = [
        ":runner",
        "//first_party/kale/model:result",
        "//first_party/kale/model:versions",
        "//first_party/kale/model/testing:sources",
        "//first_party/kale/testing:basic_processor",
        "//first_party/kale/testing:class_collecting_processor",
        "//first_party/kale/testing:exception_throwing_processor",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
        "@com_jackbradshaw_maven//:junit_junit",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlin_kotlin_test",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_test(
    name = "impl_test",
    srcs = [
        "ProcessorRunnerImplTest.kt",
    ],
    test_class = "com.jackbradshaw.kale.processor.ProcessorRunnerImplTest",
    deps = [
        ":component_impl",
        ":runner",
        ":test",
        "@com_jackbradshaw_maven//:junit_junit",
    ],
)
