load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("//first_party/dagger:defs.bzl", "kt_jvm_library_with_dagger")

kt_jvm_library(
    name = "writer",
    srcs = ["Writer.kt"],
    visibility = ["//visibility:public"],
    deps = [
        "@com_jackbradshaw_maven//:com_google_devtools_ksp_symbol_processing_api",
        "@com_jackbradshaw_maven//:com_squareup_kotlinpoet",
    ],
)

kt_jvm_library_with_dagger(
    name = "impl",
    srcs = ["WriterImpl.kt"],
    visibility = ["//visibility:public"],
    deps = [
        ":writer",
        "//first_party/backstab/processor:scope",
        "@com_jackbradshaw_maven//:com_google_devtools_ksp_symbol_processing_api",
        "@com_jackbradshaw_maven//:com_squareup_kotlinpoet",
    ],
)

kt_jvm_library_with_dagger(
    name = "module",
    srcs = ["WriterModule.kt"],
    visibility = ["//visibility:public"],
    deps = [
        ":impl",
        ":writer",
        "@com_jackbradshaw_maven//:com_google_devtools_ksp_symbol_processing_api",
    ],
)
