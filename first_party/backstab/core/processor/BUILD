load("//first_party/dagger:defs.bzl", "kt_jvm_library_with_dagger", "kt_jvm_test_with_dagger")

kt_jvm_library_with_dagger(
    name = "processor",
    srcs = ["Processor.kt", "ProcessorImpl.kt", "ProcessorModule.kt"],
    visibility = ["//visibility:public"],
    deps = [
        "//first_party/backstab/core/generator",
        "//first_party/backstab/core/model",
        "//first_party/backstab/core/parser",
        "//first_party/backstab/core/writer",
        "@com_jackbradshaw_maven//:com_google_devtools_ksp_symbol_processing_api",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_test_with_dagger(
    name = "ProcessorTest",
    srcs = ["ProcessorTest.kt", "ProcessorImplTest.kt"],
    test_class = "com.jackbradshaw.backstab.core.processor.ProcessorImplTest",
    deps = [
        ":processor",
        "//first_party/backstab/core/generator",
        "//first_party/backstab/core/model",
        "//first_party/backstab/core/parser",
        "//first_party/backstab/core/writer",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
        "@com_jackbradshaw_maven//:junit_junit",
        "@com_jackbradshaw_maven//:com_squareup_kotlinpoet",
    ],
)
