load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("//first_party/dagger:defs.bzl", "kt_jvm_library_with_dagger")

kt_jvm_library_with_dagger(
    name = "impl",
    srcs = ["EntryPoint.kt"],
    resource_strip_prefix = "first_party/backstab/ksp/entrypoint/resources",
    resources = glob(["resources/**"]),
    visibility = ["//visibility:public"],
    deps = [
        "//first_party/backstab/core:scope",
        "//first_party/backstab/core/generator:module_impl",
        "//first_party/backstab/core/main:module_impl",
        "//first_party/backstab/core/ports/errorsink",
        "//first_party/backstab/core/ports/modulesink",
        "//first_party/backstab/core/ports/targetsource",
        "//first_party/backstab/ksp/parser:module_impl",
        "//first_party/backstab/ksp/processor:module_impl",
        "//first_party/coroutines",
        "@com_jackbradshaw_maven//:com_google_devtools_ksp_symbol_processing_api",
    ],
)
