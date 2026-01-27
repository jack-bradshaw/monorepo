load("//first_party/dagger:defs.bzl", "kt_jvm_library_with_dagger")

kt_jvm_library_with_dagger(
    name = "parser",
    srcs = ["Parser.kt", "ParserImpl.kt", "ParserModule.kt"],
    visibility = ["//visibility:public"],
    deps = [
        "//first_party/backstab/core/model",
        "@com_jackbradshaw_maven//:com_google_devtools_ksp_symbol_processing_api",
        "@com_jackbradshaw_maven//:com_squareup_kotlinpoet",
    ],
)
