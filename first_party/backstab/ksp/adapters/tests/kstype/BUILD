load("@rules_kotlin//kotlin:core.bzl", "kt_ksp_plugin")
load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library", "kt_jvm_test")

kt_jvm_library(
    name = "instrumentation",
    testonly = True,
    srcs = ["KsTypeExtensionsTest.kt"],
    resource_strip_prefix = "first_party/backstab/ksp/adapters/tests/kstype",
    resources = glob(["META-INF/**"]),
    deps = [
        "//first_party/backstab/ksp/adapters",
        "//first_party/backstab/ksp/adapters:ksp_extensions",
        "//first_party/backstab/ksp/testing:symbol_processor_test",
        "@com_jackbradshaw_maven//:com_google_devtools_ksp_symbol_processing_api",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
    ],
)

kt_ksp_plugin(
    name = "plugin",
    testonly = True,
    processor_class = "com.jackbradshaw.backstab.ksp.adapters.tests.kstype.com.jackbradshaw.oksp.entrypoint.EntryPointImpl",
    deps = [":instrumentation", "//first_party/oksp/entrypoint:entrypoint_impl_declared"],
)

kt_jvm_test(
    name = "test",
    srcs = [
        "toType_complex_input.kt",
        "toType_contravariant_input.kt",
        "toType_covariant_input.kt",
        "toType_multipleTypeArguments_input.kt",
        "toType_nestedLevel_input.kt",
        "toType_nestedTypeArguments_input.kt",
        "toType_nullable_input.kt",
        "toType_simpleLevel_input.kt",
        "toType_starProjection_input.kt",
        "toType_typeArguments_input.kt",
    ],
    plugins = [":plugin"],
    test_class = "com.jackbradshaw.backstab.ksp.adapters.tests.Stub",
    deps = [
        "//first_party/backstab/ksp/adapters/tests:stub",
        "@com_jackbradshaw_maven//:com_google_dagger_dagger",
    ],
)
