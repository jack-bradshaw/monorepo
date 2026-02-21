load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")

kt_jvm_library(
    name = "symbol_processor_test",
    testonly = True,
    srcs = ["SymbolProcessorTest.kt"],
    visibility = ["//visibility:public"],
    deps = [
        "@com_jackbradshaw_maven//:com_google_devtools_ksp_symbol_processing_api",
    ],
)
