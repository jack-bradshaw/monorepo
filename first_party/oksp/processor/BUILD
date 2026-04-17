load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("//first_party/dagger:defs.bzl", "kt_jvm_library_with_dagger")

kt_jvm_library(
    name = "processor",
    srcs = ["Processor.kt"],
    visibility = ["//visibility:public"],
    deps = [
        "//first_party/oksp/model:source_file",
        "//first_party/oksp/service",
        "@com_jackbradshaw_maven//:com_google_devtools_ksp_symbol_processing_api",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_library_with_dagger(
    name = "processor_impl",
    srcs = ["ProcessorImpl.kt"],
    visibility = ["//visibility:public"],
    deps = [
        ":processor",
        "//first_party/coroutines/io",
        "//first_party/oksp/model:source_file",
        "//first_party/oksp/service",
        "//first_party/quinn",
        "//first_party/quinn:impl",
        "@com_jackbradshaw_maven//:com_google_devtools_ksp_symbol_processing_api",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)
