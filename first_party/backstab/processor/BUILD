load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("//first_party/dagger:defs.bzl", "kt_jvm_library_with_dagger")

kt_jvm_library(
    name = "component",
    srcs = ["BackstabCoreComponent.kt"],
    visibility = ["//first_party/backstab:__subpackages__"],
    deps = [
        "//first_party/backstab/processor/core",
    ],
)

kt_jvm_library_with_dagger(
    name = "component_impl",
    srcs = ["BackstabCoreComponentImpl.kt"],
    visibility = ["//first_party/backstab:__subpackages__"],
    deps = [
        ":component",
        ":scope",
        "//first_party/backstab/processor/core:module",
        "//first_party/backstab/processor/generator:module",
        "//first_party/backstab/processor/parser:module",
        "//first_party/backstab/processor/writer:module",
        "@com_jackbradshaw_maven//:com_google_devtools_ksp_symbol_processing_api",
    ],
)

kt_jvm_library(
    name = "scope",
    srcs = ["BackstabCoreScope.kt"],
    visibility = ["//first_party/backstab:__subpackages__"],
    deps = [
        "@com_jackbradshaw_maven//:javax_inject_javax_inject",
    ],
)
