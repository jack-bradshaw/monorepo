load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")

kt_jvm_library(
    name = "source",
    srcs = ["Source.kt"],
    visibility = ["//visibility:public"],
)

kt_jvm_library(
    name = "log_level",
    srcs = ["LogLevel.kt"],
    visibility = ["//visibility:public"],
)

kt_jvm_library(
    name = "ksp_context",
    srcs = ["KspContext.kt"],
    visibility = ["//visibility:public"],
    deps = [
        "@com_jackbradshaw_maven//:com_google_devtools_ksp_symbol_processing_api",
    ],
)

kt_jvm_library(
    name = "resource",
    srcs = ["Resource.kt"],
    visibility = ["//visibility:public"],
)
