load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("//first_party/dagger:defs.bzl", "kt_jvm_library_with_dagger")

kt_jvm_library(
    name = "entrypoint",
    srcs = ["EntryPoint.kt"],
    visibility = ["//visibility:public"],
    deps = [
        "//first_party/oksp/processor:processor",
        "@com_jackbradshaw_maven//:com_google_devtools_ksp_symbol_processing_api",
    ],
)

kt_jvm_library_with_dagger(
    name = "entrypoint_impl",
    srcs = ["EntryPointImpl.kt"],
    visibility = ["//visibility:public"],
    deps = [
        ":entrypoint",
        "//first_party/coroutines:coroutines",
        "//first_party/coroutines/io",
        "//first_party/oksp/application:application",
        "//first_party/oksp/application:application_loader",
        "//first_party/oksp/application:application_loader_impl",
        "//first_party/oksp/application:component",
        "//first_party/oksp/processor:processor",
        "//first_party/oksp/processor:processor_impl",
        "//first_party/oksp/services:lifecycle_service",
        "//first_party/oksp/services:processing_service",
        "@com_jackbradshaw_maven//:com_google_devtools_ksp_symbol_processing_api",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)
