load("@rules_kotlin//kotlin:core.bzl", "kt_ksp_plugin")
load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library", "kt_jvm_test")
load("//first_party/dagger:defs.bzl", "kt_jvm_library_with_dagger")

kt_jvm_library_with_dagger(
    name = "instrumentation",
    testonly = True,
    srcs = ["ParserImplTest.kt"],
    resource_strip_prefix = "first_party/backstab/ksp/parser/tests",
    resources = glob(["META-INF/**"]),
    deps = [
        "//first_party/backstab/ksp/parser:impl",
        "//first_party/backstab/ksp/parser:module_impl",
        "//first_party/backstab/ksp/testing:symbol_processor_test",
        "@com_jackbradshaw_maven//:com_google_devtools_ksp_symbol_processing_api",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
    ],
)

kt_ksp_plugin(
    name = "plugin",
    testonly = True,
    processor_class = "com.jackbradshaw.oksp.entrypoint.EntryPointImpl",
    deps = [
        ":instrumentation",
        "//first_party/oksp/entrypoint:entrypoint_impl_declared",
    ],
)

kt_jvm_test(
    name = "test",
    srcs = [
        "Stub.kt",
        "parse_backstab_only_input.kt",
        "parse_builder_custom_input.kt",
        "parse_builder_standard_input.kt",
        "parse_component_only_input.kt",
        "parse_create_input.kt",
        "parse_factory_custom_input.kt",
        "parse_factory_standard_input.kt",
        "parse_no_annotations_input.kt",
    ],
    plugins = [":plugin"],
    test_class = "com.jackbradshaw.backstab.ksp.parser.tests.Stub",
    deps = [
        "//first_party/backstab/core/annotations:backstab",
        "@com_jackbradshaw_maven//:com_google_dagger_dagger",
        "@com_jackbradshaw_maven//:javax_inject_javax_inject",
        "@com_jackbradshaw_maven//:junit_junit",
    ],
)
