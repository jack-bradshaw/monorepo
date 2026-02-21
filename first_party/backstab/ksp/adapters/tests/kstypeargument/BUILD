load("@rules_kotlin//kotlin:core.bzl", "kt_ksp_plugin")
load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library", "kt_jvm_test")

kt_jvm_library(
    name = "common",
    testonly = True,
    srcs = ["common_types.kt"],
)

kt_jvm_library(
    name = "instrumentation",
    testonly = True,
    srcs = ["KsTypeArgumentExtensionsTest.kt"],
    resource_strip_prefix = "first_party/backstab/ksp/adapters/tests/kstypeargument",
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
    processor_class = "com.jackbradshaw.backstab.ksp.adapters.tests.kstypeargument.KsTypeArgumentExtensionsTest$Provider",
    deps = [":instrumentation"],
)

kt_jvm_test(
    name = "test",
    srcs = [
        "contravariant_long_input.kt",
        "contravariant_nested_input.kt",
        "contravariant_nullable_input.kt",
        "contravariant_simple_input.kt",
        "covariant_long_input.kt",
        "covariant_nested_input.kt",
        "covariant_nullable_input.kt",
        "covariant_simple_input.kt",
        "invariant_long_input.kt",
        "invariant_nested_input.kt",
        "invariant_nullable_input.kt",
        "invariant_simple_input.kt",
        "star_input.kt",
    ],
    plugins = [":plugin"],
    test_class = "com.jackbradshaw.backstab.ksp.adapters.tests.Stub",
    deps = [
        ":common",
        "//first_party/backstab/ksp/adapters/tests:stub",
    ],
)
