load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("//first_party/dagger:defs.bzl", "kt_jvm_library_with_dagger")

kt_jvm_library(
    name = "application",
    srcs = ["ApplicationTestRule.kt"],
    visibility = ["//visibility:public"],
    deps = [
        "//first_party/kale/ksprunner",
        "//first_party/oksp/application",
        "//first_party/oksp/model:source_file",
        "@com_jackbradshaw_maven//:com_google_devtools_ksp_symbol_processing_api",
        "@com_jackbradshaw_maven//:junit_junit",
    ],
)

kt_jvm_library_with_dagger(
    name = "impl",
    srcs = ["ApplicationTestRuleImpl.kt"],
    visibility = ["//visibility:public"],
    deps = [
        ":application",
        "//first_party/kale/ksprunner",
        "//first_party/oksp/application",
        "//first_party/oksp/entrypoint",
        "@com_jackbradshaw_maven//:com_google_devtools_ksp_symbol_processing_api",
        "@com_jackbradshaw_maven//:junit_junit",
    ],
)

kt_jvm_library_with_dagger(
    name = "module",
    srcs = ["ApplicationTestModule.kt"],
    visibility = ["//visibility:public"],
    deps = [
        ":application",
        ":impl",
    ],
)

kt_jvm_library_with_dagger(
    name = "factory",
    srcs = ["ApplicationTestRuleFactory.kt"],
    visibility = ["//visibility:public"],
    deps = [
        ":application",
        ":module",
        "//first_party/coroutines:component",
        "//first_party/coroutines:component_impl",
        "//first_party/kale/ksprunner:component",
        "//first_party/kale/ksprunner:component_impl",
        "//first_party/oksp/application",
    ],
)
