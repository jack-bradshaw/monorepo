load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_test")
load("//first_party/dagger:defs.bzl", "kt_jvm_test_with_dagger")

kt_jvm_test_with_dagger(
    name = "test",
    srcs = ["ParserImplTest.kt"],
    test_class = "com.jackbradshaw.backstab.ksp.parser.tests.ParserImplTest",
    deps = [
        "//first_party/backstab/core/annotations:backstab",
        "//first_party/backstab/core/model:module",
        "//first_party/backstab/ksp/parser:impl",
        "//first_party/backstab/ksp/parser:module_impl",
        "//first_party/kale/resolver/rule:factory",
        "//first_party/oksp/model:source_file",
        "@com_jackbradshaw_maven//:com_google_devtools_ksp_symbol_processing_api",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
        "@com_jackbradshaw_maven//:junit_junit",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)
