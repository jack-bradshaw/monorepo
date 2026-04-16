load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library", "kt_jvm_test")

kt_jvm_library(
    name = "adapters",
    srcs = [
        "ksAnnotation.kt",
        "ksDeclaration.kt",
        "ksType.kt",
        "ksTypeArgument.kt",
    ],
    visibility = ["//first_party/backstab:__subpackages__"],
    deps = [
        "//first_party/backstab/core/model:target",
        "//first_party/backstab/core/model:type",
        "//first_party/backstab/core/typeregistry:javax",
        "@com_jackbradshaw_maven//:com_google_devtools_ksp_symbol_processing_api",
    ],
)

kt_jvm_test(
    name = "ks_type_test",
    srcs = ["KsTypeTest.kt"],
    test_class = "com.jackbradshaw.backstab.ksp.adapters.tests.kstype.KsTypeExtensionsTest",
    deps = [
        "//first_party/backstab/ksp/adapters",
        "//first_party/kale/resolver/rule:factory",
        "@com_jackbradshaw_maven//:com_google_devtools_ksp_symbol_processing_api",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
        "@com_jackbradshaw_maven//:junit_junit",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_test(
    name = "ks_declaration_test",
    srcs = ["KsDeclarationTest.kt"],
    test_class = "com.jackbradshaw.backstab.ksp.adapters.tests.ksdeclaration.KsDeclarationExtensionsTest",
    deps = [
        "//first_party/backstab/ksp/adapters",
        "//first_party/kale/resolver/rule:factory",
        "@com_jackbradshaw_maven//:com_google_devtools_ksp_symbol_processing_api",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
        "@com_jackbradshaw_maven//:junit_junit",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_test(
    name = "ks_type_argument_test",
    srcs = ["KsTypeArgumentTest.kt"],
    test_class = "com.jackbradshaw.backstab.ksp.adapters.tests.kstypeargument.KsTypeArgumentExtensionsTest",
    deps = [
        "//first_party/backstab/ksp/adapters",
        "//first_party/kale/resolver/rule:factory",
        "@com_jackbradshaw_maven//:com_google_devtools_ksp_symbol_processing_api",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
        "@com_jackbradshaw_maven//:junit_junit",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_test(
    name = "ks_annotation_test",
    srcs = ["KsAnnotationTest.kt"],
    test_class = "com.jackbradshaw.backstab.ksp.adapters.tests.ksannotation.KsAnnotationExtensionsTest",
    deps = [
        "//first_party/backstab/core/model:target",
        "//first_party/backstab/ksp/adapters",
        "//first_party/kale/ksprunner",
        "//first_party/kale/resolver/rule:factory",
        "@com_jackbradshaw_maven//:com_google_devtools_ksp_symbol_processing_api",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
        "@com_jackbradshaw_maven//:junit_junit",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)
