load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("//first_party/dagger:defs.bzl", "kt_jvm_library_with_dagger")

kt_jvm_library(
    name = "processor",
    srcs = ["Processor.kt"],
    visibility = ["//first_party/backstab:__subpackages__"],
    deps = [
        "//first_party/backstab/core/ports/errorsink",
        "//first_party/backstab/core/ports/modulesink",
        "//first_party/backstab/core/ports/targetsource",
        "@com_jackbradshaw_maven//:com_google_devtools_ksp_symbol_processing_api",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_library_with_dagger(
    name = "impl",
    srcs = ["ProcessorImpl.kt"],
    visibility = ["//first_party/backstab:__subpackages__"],
    deps = [
        ":processor",
        "//first_party/backstab/core:scope",
        "//first_party/backstab/core/annotations:aggregate_scope",
        "//first_party/backstab/core/annotations:backstab",
        "//first_party/backstab/core/model:module",
        "//first_party/backstab/core/model:source_header",
        "//first_party/backstab/core/model:target",
        "//first_party/backstab/core/model:type",
        "//first_party/backstab/core/ports/errorsink",
        "//first_party/backstab/core/ports/modulesink",
        "//first_party/backstab/core/ports/targetsource",
        "//first_party/backstab/core/typeregistry:backstab",
        "//first_party/backstab/ksp/parser",
        "//first_party/coroutines/io",
        "@com_jackbradshaw_maven//:com_google_devtools_ksp_symbol_processing_api",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_library_with_dagger(
    name = "module_impl",
    srcs = ["ProcessorImplModule.kt"],
    visibility = ["//first_party/backstab:__subpackages__"],
    deps = [
        ":impl",
        ":processor",
        "//first_party/backstab/core/ports/errorsink",
        "//first_party/backstab/core/ports/modulesink",
        "//first_party/backstab/core/ports/targetsource",
        "@com_jackbradshaw_maven//:com_google_devtools_ksp_symbol_processing_api",
    ],
)
