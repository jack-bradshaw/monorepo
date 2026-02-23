load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("//first_party/dagger:defs.bzl", "kt_jvm_library_with_dagger")

kt_jvm_library(
    name = "application",
    srcs = ["Application.kt"],
    visibility = ["//visibility:public"],
    deps = [
        ":component",
    ],
)

kt_jvm_library(
    name = "application_loader",
    srcs = ["ApplicationLoader.kt"],
    visibility = ["//visibility:public"],
    deps = [":application"],
)

kt_jvm_library_with_dagger(
    name = "application_loader_impl",
    srcs = ["ApplicationLoaderImpl.kt"],
    visibility = ["//visibility:public"],
    deps = [
        ":application",
        ":application_loader",
    ],
)

kt_jvm_library(
    name = "component",
    srcs = ["ApplicationComponent.kt"],
    visibility = ["//visibility:public"],
    deps = [
        "//first_party/oksp/processor:processor",
        "//first_party/oksp/service",
        "@com_jackbradshaw_maven//:com_google_devtools_ksp_symbol_processing_api",
    ],
)