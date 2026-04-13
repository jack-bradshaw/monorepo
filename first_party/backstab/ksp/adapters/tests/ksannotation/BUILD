load("@rules_kotlin//kotlin:core.bzl", "kt_ksp_plugin")
load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library", "kt_jvm_test")

kt_jvm_library(
    name = "instrumentation",
    testonly = True,
    srcs = ["KsAnnotationExtensionsTest.kt"],
    resource_strip_prefix = "first_party/backstab/ksp/adapters/tests/ksannotation",
    resources = glob(["META-INF/**"]),
    deps = [
        "//first_party/backstab/core/model:target",
        "//first_party/backstab/ksp/adapters",
        "//first_party/backstab/ksp/testing:symbol_processor_test",
        "@com_jackbradshaw_maven//:com_google_devtools_ksp_symbol_processing_api",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
    ],
)

kt_ksp_plugin(
    name = "plugin",
    testonly = True,
    processor_class = "com.jackbradshaw.backstab.ksp.adapters.tests.ksannotation.com.jackbradshaw.oksp.entrypoint.EntryPointImpl",
    deps = [":instrumentation", "//first_party/oksp/entrypoint:entrypoint_impl_declared"],
)

kt_jvm_test(
    name = "test",
    srcs = [
        "toQualifier_customQualifier_input.kt",
        "toQualifier_namedAnnotation_input.kt",
        "toQualifier_notQualifier_input.kt",
    ],
    plugins = [":plugin"],
    test_class = "com.jackbradshaw.backstab.ksp.adapters.tests.Stub",
    deps = [
        "//first_party/backstab/ksp/adapters/tests:stub",
        "@com_jackbradshaw_maven//:com_google_dagger_dagger",
    ],
)
