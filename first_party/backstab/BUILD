load("@rules_kotlin//kotlin:core.bzl", "kt_ksp_plugin")
load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library", "kt_jvm_test")
load("//first_party/backstab:publicity.bzl", "PUBLICITY")
load("//first_party/dagger:defs.bzl", "kt_jvm_library_with_dagger")

kt_jvm_library(
    name = "entrypoint",
    srcs = ["BackstabKsp.kt"],
    resource_strip_prefix = "first_party/backstab/resources",
    resources = glob(["resources/**"]),
    visibility = ["//first_party/backstab:__subpackages__"],
    deps = [
        "//first_party/backstab/annotations:backstab",
        "//first_party/backstab/processor:component_impl",
        "//first_party/backstab/processor:processor_lib",
        "@com_jackbradshaw_maven//:com_google_devtools_ksp_symbol_processing_api",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_ksp_plugin(
    name = "plugin",
    processor_class = "com.jackbradshaw.backstab.BackstabProcessorProvider",
    visibility = PUBLICITY,
    deps = [":entrypoint"],
)

kt_jvm_library_with_dagger(
    name = "testing_infrastructure",
    testonly = True,
    srcs = ["TestingComponents.kt"],
    plugins = [
        ":plugin",
    ],
    visibility = ["//visibility:private"],
    deps = [
        "//first_party/backstab/annotations:aggregate",
        "//first_party/backstab/annotations:backstab",
        "@com_jackbradshaw_maven//:com_google_code_findbugs_jsr305",
    ],
)

kt_jvm_test(
    name = "BackstabKspTest",
    srcs = ["BackstabKspTest.kt"],
    test_class = "com.jackbradshaw.backstab.BackstabKspTest",
    deps = [
        ":testing_infrastructure",
        "//first_party/backstab/annotations:aggregate",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
        "@com_jackbradshaw_maven//:junit_junit",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlin_kotlin_reflect",
    ],
)
