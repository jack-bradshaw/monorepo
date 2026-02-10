load("@rules_kotlin//kotlin:core.bzl", "kt_ksp_plugin")
load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("//first_party/backstab:publicity.bzl", "PUBLICITY")

kt_ksp_plugin(
    name = "plugin",
    processor_class = "com.jackbradshaw.backstab.entrypoint.BackstabProcessorProvider",
    visibility = PUBLICITY,
    deps = [":entrypoint"],
)

kt_jvm_library(
    name = "entrypoint",
    srcs = ["BackstabProcessor.kt"],
    resource_strip_prefix = "first_party/backstab/entrypoint/resources",
    resources = glob(["resources/**"]),
    visibility = ["//first_party/backstab:__subpackages__"],
    deps = [
        "//first_party/backstab/annotations/backstab",
        "//first_party/backstab/processor:component_impl",
        "//first_party/backstab/processor/core",
        "@com_jackbradshaw_maven//:com_google_devtools_ksp_symbol_processing_api",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)
