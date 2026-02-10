load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("//first_party/dagger:defs.bzl", "kt_jvm_library_with_dagger", "kt_jvm_test_with_dagger")
load("//first_party/backstab:publicity.bzl", "PUBLICITY")

kt_jvm_library(
    name = "core",
    srcs = ["Processor.kt"],
    visibility = PUBLICITY,
    deps = [
        "@com_jackbradshaw_maven//:com_google_devtools_ksp_symbol_processing_api",
    ],
)

kt_jvm_library(
    name = "impl",
    srcs = ["ProcessorImpl.kt"],
    visibility = PUBLICITY,
    deps = [
        ":core",
        "//first_party/backstab/processor:scope",
        "//first_party/backstab/processor/generator",
        "//first_party/backstab/processor/parser",
        "//first_party/backstab/processor/writer",
        "@com_jackbradshaw_maven//:com_google_devtools_ksp_symbol_processing_api",
        "@com_jackbradshaw_maven//:javax_inject_javax_inject",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_library_with_dagger(
    name = "module",
    srcs = ["ProcessorModule.kt"],
    visibility = ["//visibility:public"],
    deps = [
        ":core",
        ":impl",
    ],
)

kt_jvm_test_with_dagger(
    name = "ProcessorTest",
    srcs = [
        "ProcessorImplTest.kt",
        "ProcessorTest.kt",
    ],
    test_class = "com.jackbradshaw.backstab.processor.core.ProcessorImplTest",
    deps = [
        ":core",
        ":impl",
        ":module",
        "//first_party/backstab/processor:scope",
        "//first_party/backstab/processor/generator",
        "//first_party/backstab/processor/model",
        "//first_party/backstab/processor/parser",
        "//first_party/backstab/processor/writer",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
        "@com_jackbradshaw_maven//:com_squareup_kotlinpoet",
        "@com_jackbradshaw_maven//:junit_junit",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)
