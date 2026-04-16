load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("//first_party/dagger:defs.bzl", "kt_jvm_library_with_dagger")

kt_jvm_library_with_dagger(
    name = "impl",
    srcs = [
        "Repository.kt",
        "RepositoryImpl.kt",
    ],
    visibility = ["//first_party/backstab:__subpackages__"],
    deps = [
        "//first_party/backstab/core:scope",
        "//first_party/backstab/core/annotations:aggregate_scope",
        "//first_party/backstab/core/annotations:backstab",
        "//first_party/backstab/core/model:module",
        "//first_party/backstab/core/model:target",
        "//first_party/backstab/core/model:type",
        "//first_party/backstab/core/repository",
        "//first_party/backstab/core/typeregistry:backstab",
        "//first_party/backstab/ksp/parser",
        "//first_party/coroutines",
        "//first_party/coroutines/io",
        "//first_party/oksp/application:component",
        "//first_party/oksp/model:source_file",
        "//first_party/oksp/service",
        "@com_jackbradshaw_maven//:com_google_devtools_ksp_symbol_processing_api",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_library_with_dagger(
    name = "module_impl",
    srcs = [
        "RepositoryImplModule.kt",
    ],
    visibility = ["//first_party/backstab:__subpackages__"],
    deps = [
        ":impl",
        "//first_party/backstab/core/repository",
        "//first_party/backstab/ksp/parser:module_impl",
        "//first_party/coroutines",
        "//first_party/coroutines/io",
        "//first_party/oksp/application:component",
        "@com_jackbradshaw_maven//:com_google_devtools_ksp_symbol_processing_api",
    ],
)
