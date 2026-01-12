load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("@rules_kotlin//kotlin:core.bzl", "kt_ksp_plugin")

kt_jvm_library(
    name = "processor",
    srcs = glob(["*.kt"]),
    resources = glob(["resources/**"]),
    # We strip the prefix so META-INF ends up at root of jar
    resource_strip_prefix = "first_party/backstab/processor/resources",
    deps = [
        "//first_party/backstab/annotations",
        "@com_jackbradshaw_maven//:com_google_dagger_dagger",
        "@com_jackbradshaw_maven//:com_google_devtools_ksp_symbol_processing_api",
        "@com_jackbradshaw_maven//:com_squareup_kotlinpoet",
    ],
    visibility = ["//visibility:public"],
)

kt_ksp_plugin(
    name = "plugin",
    processor_class = "com.jackbradshaw.backstab.processor.BackstabProcessorProvider",
    deps = [":processor"],
    visibility = ["//visibility:public"],
)
