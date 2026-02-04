load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("//first_party/dagger:defs.bzl", "kt_jvm_library_with_dagger")

kt_jvm_library(
    name = "parser",
    srcs = ["Parser.kt"],
    visibility = ["//visibility:public"],
    deps = [
        "//first_party/backstab/processor/model",
        "@com_jackbradshaw_maven//:com_google_devtools_ksp_symbol_processing_api",
    ],
)

kt_jvm_library_with_dagger(
    name = "impl",
    srcs = ["ParserImpl.kt"],
    visibility = ["//visibility:public"],
    deps = [
        ":parser",
        "//first_party/backstab/external",
        "//first_party/backstab/processor:scope",
        "//first_party/backstab/processor/model",
        "@com_jackbradshaw_maven//:com_google_devtools_ksp_symbol_processing_api",
        "@com_jackbradshaw_maven//:com_squareup_kotlinpoet",
        "@com_jackbradshaw_maven//:com_squareup_kotlinpoet_ksp",
    ],
)

kt_jvm_library_with_dagger(
    name = "module",
    srcs = ["ParserModule.kt"],
    visibility = ["//visibility:public"],
    deps = [
        ":impl",
        ":parser",
    ],
)
