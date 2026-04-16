load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library", "kt_jvm_test")
load("//first_party/dagger:defs.bzl", "kt_jvm_library_with_dagger", "kt_jvm_test_with_dagger")
load("//first_party/kale:publicity.bzl", "PUBLICITY")

kt_jvm_library(
    name = "ksprunner",
    srcs = ["KspRunner.kt"],
    visibility = PUBLICITY,
    deps = [
        "//first_party/kale/model:artefacts",
        "//first_party/kale/model:source",
        "//first_party/kale/model:versions",
        "@com_jackbradshaw_maven//:com_google_devtools_ksp_symbol_processing_api",
    ],
)

kt_jvm_library_with_dagger(
    name = "impl",
    srcs = ["KspRunnerImpl.kt"],
    visibility = ["//first_party:__subpackages__"],
    deps = [
        ":ksprunner",
        "//first_party/kale/model:artefacts",
        "//first_party/kale/model:class",
        "//first_party/kale/model:source",
        "//first_party/kale/model:versions",
        "@com_jackbradshaw_maven//:com_google_devtools_ksp_symbol_processing_aa_embeddable",
        "@com_jackbradshaw_maven//:com_google_devtools_ksp_symbol_processing_api",
        "@com_jackbradshaw_maven//:com_google_devtools_ksp_symbol_processing_cmdline",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlin_kotlin_compiler",
    ],
)

kt_jvm_library_with_dagger(
    name = "module",
    srcs = ["KspRunnerModule.kt"],
    visibility = ["//visibility:public"],
    deps = [
        ":impl",
        ":ksprunner",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlin_kotlin_compiler",
    ],
)

kt_jvm_library(
    name = "test",
    testonly = True,
    srcs = ["KspRunnerTest.kt"],
    visibility = PUBLICITY,
    deps = [
        ":ksprunner",
        "//first_party/kale/model:artefacts",
        "//first_party/kale/model:class",
        "//first_party/kale/model:source",
        "//first_party/kale/model:versions",
        "//first_party/kale/model/testing:sources",
        "//first_party/kale/testing:basic_provider",
        "//first_party/kale/testing:class_collecting_provider",
        "//first_party/kale/testing:code_generating_provider",
        "//first_party/kale/testing:exception_throwing_provider",
        "//first_party/kale/testing:options_collecting_provider",
        "@com_jackbradshaw_maven//:com_google_devtools_ksp_symbol_processing_api",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
        "@com_jackbradshaw_maven//:junit_junit",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_test_with_dagger(
    name = "impl_test",
    srcs = ["KspRunnerImplTest.kt"],
    test_class = "com.jackbradshaw.kale.ksprunner.KspRunnerImplTest",
    deps = [
        ":ksprunner",
        ":module",
        ":test",
        "//first_party/coroutines:component",
        "//first_party/coroutines:component_impl",
        "//first_party/coroutines/testing/realistic:component",
        "//first_party/coroutines/testing/realistic:component_impl",
        "//first_party/kale:scope",
        "//first_party/kale/model:artefacts",
        "//first_party/kale/model:class",
        "//first_party/kale/model:source",
        "//first_party/kale/model:versions",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
        "@com_jackbradshaw_maven//:junit_junit",
    ],
)
