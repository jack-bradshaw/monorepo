load("@rules_kotlin//kotlin:core.bzl", "kt_ksp_plugin")
load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")

kt_ksp_plugin(
    name = "plugin",
    processor_class = "com.jackbradshaw.backstab.entrypoint.BackstabProcessorProvider",
    visibility = ["//visibility:public"],
    deps = [":entrypoint"],
)

kt_jvm_library(
    name = "entrypoint",
    srcs = ["BackstabProcessor.kt"],
    resource_strip_prefix = "first_party/backstab/entrypoint/resources",
    resources = glob(["resources/**"]),
    visibility = ["//visibility:public"],
    deps = [
        "//first_party/backstab/annotations/backstab",
        "//first_party/backstab/core",
        "//first_party/backstab/core/processor",
        "@com_jackbradshaw_maven//:com_google_devtools_ksp_symbol_processing_api",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)
