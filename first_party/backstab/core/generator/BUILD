load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("//first_party/dagger:defs.bzl", "kt_jvm_library_with_dagger", "kt_jvm_test_with_dagger")

kt_jvm_library(
    name = "generator",
    srcs = ["Generator.kt"],
    visibility = ["//first_party/backstab:__subpackages__"],
    deps = [
        "//first_party/backstab/core/model:module",
        "//first_party/oksp/model:source_file",
        "//first_party/backstab/core/model:target",
    ],
)

kt_jvm_library_with_dagger(
    name = "impl",
    srcs = ["GeneratorImpl.kt"],
    visibility = ["//first_party/backstab:__subpackages__"],
    deps = [
        ":generator",
        "//first_party/backstab/core:scope",
        "//first_party/backstab/core/annotations:aggregate_scope",
        "//first_party/backstab/core/model:module",
        "//first_party/oksp/model:source_file",
        "//first_party/backstab/core/model:target",
        "//first_party/backstab/core/model:type",
        "//first_party/backstab/core/model/kotlinpoet:backstab_target",
        "//first_party/backstab/core/model/kotlinpoet:type",
        "//first_party/backstab/core/typeregistry:backstab",
        "//first_party/backstab/core/typeregistry:dagger",
        "//first_party/backstab/core/typeregistry:javax",
        "@com_jackbradshaw_maven//:com_squareup_kotlinpoet",
    ],
)

kt_jvm_library_with_dagger(
    name = "module_impl",
    srcs = ["GeneratorModule.kt"],
    visibility = ["//first_party/backstab:__subpackages__"],
    deps = [
        ":generator",
        ":impl",
    ],
)

kt_jvm_test_with_dagger(
    name = "impl_test",
    srcs = [
        "GeneratorImplTest.kt",
        "GeneratorTest.kt",
    ],
    test_class = "com.jackbradshaw.backstab.core.generator.GeneratorImplTest",
    deps = [
        ":generator",
        ":impl",
        ":module_impl",
        "//first_party/backstab/core/annotations:aggregate_scope",
        "//first_party/oksp/model:source_file",
        "//first_party/backstab/core/model:target",
        "//first_party/backstab/core/model:type",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
        "@com_jackbradshaw_maven//:com_squareup_kotlinpoet",
        "@com_jackbradshaw_maven//:junit_junit",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)
