load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("//first_party/dagger:defs.bzl", "kt_jvm_library_with_dagger", "kt_jvm_test_with_dagger")

kt_jvm_library(
    name = "processor_lib",
    srcs = ["Processor.kt"],
    visibility = ["//first_party/backstab:__subpackages__"],
    deps = [
        "@com_jackbradshaw_maven//:com_google_devtools_ksp_symbol_processing_api",
    ],
)

kt_jvm_library(
    name = "impl",
    srcs = ["ProcessorImpl.kt"],
    visibility = ["//first_party/backstab:__subpackages__"],
    deps = [
        ":processor_lib",
        ":scope",
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
    srcs = ["ProcessorImplModule.kt"],
    visibility = ["//first_party/backstab:__subpackages__"],
    deps = [
        ":impl",
        ":processor_lib",
    ],
)

kt_jvm_test_with_dagger(
    name = "ProcessorTest",
    srcs = [
        "ProcessorImplTest.kt",
        "ProcessorTest.kt",
    ],
    test_class = "com.jackbradshaw.backstab.processor.ProcessorImplTest",
    deps = [
        ":impl",
        ":module",
        ":processor_lib",
        ":scope",
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

kt_jvm_library(
    name = "component",
    srcs = ["ProcessorComponent.kt"],
    visibility = ["//first_party/backstab:__subpackages__"],
    deps = [
        ":processor_lib",
    ],
)

kt_jvm_library_with_dagger(
    name = "component_impl",
    srcs = ["ProcessorComponentImpl.kt"],
    visibility = ["//first_party/backstab:__subpackages__"],
    deps = [
        ":component",
        ":module",
        ":scope",
        "//first_party/backstab/processor/generator:module",
        "//first_party/backstab/processor/parser:module",
        "//first_party/backstab/processor/writer:module",
        "@com_jackbradshaw_maven//:com_google_devtools_ksp_symbol_processing_api",
    ],
)

kt_jvm_library(
    name = "scope",
    srcs = ["ProcessorScope.kt"],
    visibility = ["//first_party/backstab:__subpackages__"],
    deps = [
        "@com_jackbradshaw_maven//:javax_inject_javax_inject",
    ],
)
