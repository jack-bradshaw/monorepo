load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library", "kt_jvm_test")

kt_jvm_library(
    name = "adapters",
    srcs = [
        "ksAnnotation.kt",
        "ksDeclaration.kt",
        "ksType.kt",
        "ksTypeArgument.kt",
        "BackstabInflowAdapter.kt",
        "BackstabOutflowAdapter.kt",
    ],
    visibility = ["//first_party/backstab:__subpackages__"],
    deps = [
        "//first_party/backstab/core/model:target",
        "//first_party/backstab/core/model:module",
        "//first_party/backstab/core/model:type",
        "//first_party/backstab/core/typeregistry:javax",
        "//first_party/backstab/core/typeregistry:backstab",
        "//first_party/backstab/core/typeregistry:dagger",
        "//first_party/obelisk/core/adapters:inflow_adapter",
        "//first_party/obelisk/core/adapters:outflow_adapter",
        "@com_jackbradshaw_maven//:javax_inject_javax_inject",
        "@com_jackbradshaw_maven//:com_google_devtools_ksp_symbol_processing_api",
    ],
)

kt_jvm_test(
    name = "ks_type_test",
    srcs = ["KsTypeTest.kt"],
    test_class = "com.jackbradshaw.backstab.oksp.adapters.KsTypeTest",
    deps = [
        "//first_party/backstab/oksp/adapters",
        "//first_party/kale/resolver/rule:rule",
        "@com_jackbradshaw_maven//:com_google_devtools_ksp_symbol_processing_api",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
        "@com_jackbradshaw_maven//:junit_junit",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_test(
    name = "ks_declaration_test",
    srcs = ["KsDeclarationTest.kt"],
    test_class = "com.jackbradshaw.backstab.oksp.adapters.KsDeclarationTest",
    deps = [
        "//first_party/backstab/oksp/adapters",
        "//first_party/kale/resolver/rule:rule",
        "@com_jackbradshaw_maven//:com_google_devtools_ksp_symbol_processing_api",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
        "@com_jackbradshaw_maven//:junit_junit",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_test(
    name = "ks_type_argument_test",
    srcs = ["KsTypeArgumentTest.kt"],
    test_class = "com.jackbradshaw.backstab.oksp.adapters.KsTypeArgumentTest",
    deps = [
        "//first_party/backstab/oksp/adapters",
        "//first_party/kale/resolver/rule:rule",
        "@com_jackbradshaw_maven//:com_google_devtools_ksp_symbol_processing_api",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
        "@com_jackbradshaw_maven//:junit_junit",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_test(
    name = "ks_annotation_test",
    srcs = ["KsAnnotationTest.kt"],
    test_class = "com.jackbradshaw.backstab.oksp.adapters.KsAnnotationTest",
    deps = [
        "//first_party/backstab/core/model:target",
        "//first_party/backstab/oksp/adapters",
        "//first_party/kale/provider:runner",
        "//first_party/kale/resolver/rule:rule",
        "@com_jackbradshaw_maven//:com_google_devtools_ksp_symbol_processing_api",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
        "@com_jackbradshaw_maven//:junit_junit",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_test(
    name = "backstab_inflow_adapter_test",
    srcs = ["BackstabInflowAdapterTest.kt"],
    test_class = "com.jackbradshaw.backstab.oksp.adapters.BackstabInflowAdapterTest",
    deps = [
        "//first_party/backstab/core/annotations:backstab",
        "//first_party/backstab/core/model:module",
        "//first_party/backstab/oksp/adapters",
        "//first_party/kale/resolver/rule:rule",
        "//first_party/obelisk/core/model:source",
        "@com_jackbradshaw_maven//:com_google_devtools_ksp_symbol_processing_api",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
        "@com_jackbradshaw_maven//:junit_junit",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)
