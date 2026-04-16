load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("//first_party/dagger:defs.bzl", "kt_jvm_library_with_dagger")

kt_jvm_library_with_dagger(
    name = "impl",
    srcs = ["BackstabApplication.kt"],
    resource_strip_prefix = "first_party/backstab/ksp/entrypoint/resources",
    resources = glob(["resources/**"]),
    visibility = ["//visibility:public"],
    deps = [
        "//first_party/backstab/core:component_impl",
        "//first_party/backstab/core:scope",
        "//first_party/backstab/core/host",
        "//first_party/backstab/core/repository",
        "//first_party/backstab/ksp/parser:module_impl",
        "//first_party/backstab/ksp/repository:module_impl",
        "//first_party/coroutines",
        "//first_party/oksp/application",
        "//first_party/oksp/application:component",
        "//first_party/oksp/service",
        "@com_jackbradshaw_maven//:com_google_devtools_ksp_symbol_processing_api",
    ],
)
