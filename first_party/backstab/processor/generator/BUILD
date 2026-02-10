load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("//first_party/dagger:defs.bzl", "kt_jvm_library_with_dagger", "kt_jvm_test_with_dagger")

kt_jvm_library(
    name = "generator",
    srcs = ["Generator.kt"],
    visibility = ["//first_party/backstab:__subpackages__"],
    deps = [
        "//first_party/backstab/processor/model",
        "@com_jackbradshaw_maven//:com_squareup_kotlinpoet",
    ],
)

kt_jvm_library_with_dagger(
    name = "impl",
    srcs = ["GeneratorImpl.kt"],
    visibility = ["//first_party/backstab:__subpackages__"],
    deps = [
        ":generator",
        "//first_party/backstab/annotations:aggregate",
        "//first_party/backstab/external:dagger",
        "//first_party/backstab/processor:scope",
        "//first_party/backstab/processor/model",
        "@com_jackbradshaw_maven//:com_squareup_kotlinpoet",
    ],
)

kt_jvm_library_with_dagger(
    name = "module",
    srcs = ["GeneratorModule.kt"],
    visibility = ["//first_party/backstab:__subpackages__"],
    deps = [
        ":generator",
        ":impl",
    ],
)

kt_jvm_test_with_dagger(
    name = "GeneratorTest",
    srcs = [
        "GeneratorImplTest.kt",
        "GeneratorTest.kt",
    ],
    test_class = "com.jackbradshaw.backstab.processor.generator.GeneratorImplTest",
    deps = [
        ":generator",
        ":impl",
        ":module",
        "//first_party/backstab/annotations:aggregate",
        "//first_party/backstab/processor:scope",
        "//first_party/backstab/processor/model",
        "//first_party/backstab/processor/parser",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
        "@com_jackbradshaw_maven//:com_squareup_kotlinpoet",
        "@com_jackbradshaw_maven//:junit_junit",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)
