load("@rules_kotlin//kotlin:core.bzl", "kt_ksp_plugin")
load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library", "kt_jvm_test")

kt_jvm_library(
    name = "instrumentation",
    testonly = True,
    srcs = ["KsDeclarationExtensionsTest.kt"],
    resource_strip_prefix = "first_party/backstab/ksp/adapters/tests/ksdeclaration",
    resources = glob(["META-INF/**"]),
    deps = [
        "//first_party/backstab/ksp/adapters:backstab_target",
        "//first_party/backstab/ksp/testing:symbol_processor_test",
        "@com_jackbradshaw_maven//:com_google_devtools_ksp_symbol_processing_api",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
    ],
)

kt_ksp_plugin(
    name = "plugin",
    testonly = True,
    processor_class = "com.jackbradshaw.backstab.ksp.adapters.tests.ksdeclaration.com.jackbradshaw.oksp.entrypoint.EntryPointImpl",
    deps = [":instrumentation", "//first_party/oksp/entrypoint:entrypoint_impl_declared"],
)

kt_jvm_test(
    name = "test",
    srcs = [
        "nameChain_deepNesting_input.kt",
        "nameChain_multipleLevels_input.kt",
        "nameChain_singleLevel_input.kt",
    ],
    plugins = [":plugin"],
    test_class = "com.jackbradshaw.backstab.ksp.adapters.tests.Stub",
    deps = [
        "//first_party/backstab/ksp/adapters/tests:stub",
    ],
)
