load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("//first_party/dagger:defs.bzl", "kt_jvm_library_with_dagger", "kt_jvm_test_with_dagger")
load("//first_party/backstab:publicity.bzl", "PUBLICITY")

kt_jvm_library(
    name = "generator",
    srcs = ["AggregateComponentGenerator.kt"],
    visibility = PUBLICITY,
    deps = [
        "//first_party/backstab/processor/model",
        "@com_jackbradshaw_maven//:com_squareup_kotlinpoet",
    ],
)

kt_jvm_library_with_dagger(
    name = "impl",
    srcs = ["AggregateComponentGeneratorImpl.kt"],
    visibility = PUBLICITY,
    deps = [
        ":generator",
        "//first_party/backstab/annotations/aggregate",
        "//first_party/backstab/processor:scope",
        "//first_party/backstab/processor/model",
        "@com_jackbradshaw_maven//:com_squareup_kotlinpoet",
    ],
)

kt_jvm_library_with_dagger(
    name = "module",
    srcs = ["AggregateComponentGeneratorModule.kt"],
    visibility = PUBLICITY,
    deps = [
        ":generator",
        ":impl",
    ],
)

kt_jvm_test_with_dagger(
    name = "AggregateComponentGeneratorTest",
    srcs = [
        "AggregateComponentGeneratorImplTest.kt",
        "AggregateComponentGeneratorTest.kt",
    ],
    test_class = "com.jackbradshaw.backstab.processor.generator.AggregateComponentGeneratorImplTest",
    deps = [
        ":generator",
        ":impl",
        ":module",
        "//first_party/backstab/annotations/aggregate",
        "//first_party/backstab/processor:scope",
        "//first_party/backstab/processor/model",
        "//first_party/backstab/processor/parser",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
        "@com_jackbradshaw_maven//:com_squareup_kotlinpoet",
        "@com_jackbradshaw_maven//:junit_junit",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)
