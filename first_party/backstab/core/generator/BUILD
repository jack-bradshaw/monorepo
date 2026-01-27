load("//first_party/dagger:defs.bzl", "kt_jvm_library_with_dagger", "kt_jvm_test_with_dagger")

kt_jvm_library_with_dagger(
    name = "generator",
    srcs = ["MetaComponentGenerator.kt", "MetaComponentGeneratorImpl.kt", "MetaComponentGeneratorModule.kt"],
    visibility = ["//visibility:public"],
    deps = [
        "//first_party/backstab/annotations/meta",
        "//first_party/backstab/core/model",
        "@com_jackbradshaw_maven//:com_squareup_kotlinpoet",
    ],
)

kt_jvm_test_with_dagger(
    name = "MetaComponentGeneratorTest",
    srcs = ["MetaComponentGeneratorTest.kt", "MetaComponentGeneratorImplTest.kt"],
    test_class = "com.jackbradshaw.backstab.core.generator.MetaComponentGeneratorImplTest",
    deps = [
        ":generator",
        "//first_party/backstab/annotations/meta",
        "//first_party/backstab/core/model",
        "@com_jackbradshaw_maven//:com_google_truth_truth",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
        "@com_jackbradshaw_maven//:junit_junit",
        "@com_jackbradshaw_maven//:com_squareup_kotlinpoet",
    ],
)
