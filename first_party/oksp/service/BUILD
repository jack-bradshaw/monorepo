load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")

kt_jvm_library(
    name = "service",
    srcs = ["KspService.kt"],
    visibility = ["//visibility:public"],
    deps = [
        "//first_party/oksp/model:ksp_context",
        "//first_party/oksp/model:log_level",
        "//first_party/oksp/model:resource",
        "//first_party/oksp/model:source",
        "@com_jackbradshaw_maven//:com_google_devtools_ksp_symbol_processing_api",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_library(
    name = "test_lib",
    testonly = True,
    srcs = ["KspServiceTest.kt"],
    visibility = ["//visibility:public"],
    deps = [
        ":service",
        "//first_party/coroutines/testing/realistic:component",
        "//first_party/coroutines/testing/realistic:component_impl",
        "//first_party/kale/model:artifacts",
        "//first_party/kale/model:log",
        "//first_party/kale/model:result",
        "//first_party/kale/model:source",
        "//first_party/oksp/model:source",
        "@com_jackbradshaw_maven//:com_google_devtools_ksp_symbol_processing_api",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
        "@com_jackbradshaw_maven//:junit_junit",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlin_kotlin_test",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
        "@com_jackbradshaw_maven//:org_mockito_mockito_core",
    ],
)
