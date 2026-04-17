load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("//first_party/dagger:defs.bzl", "kt_jvm_library_with_dagger")

kt_jvm_library(
    name = "application",
    srcs = [
        "Application.kt",
        "ApplicationComponent.kt",
    ],
    visibility = ["//visibility:public"],
    deps = [
        "//first_party/oksp/processor",
        "//first_party/oksp/service",
        "@com_jackbradshaw_maven//:com_google_devtools_ksp_symbol_processing_api",
    ],
)

kt_jvm_library_with_dagger(
    name = "impl",
    srcs = [
        "loaded/LoadedApplicationComponent.kt",
        "loaded/LoadedApplicationModule.kt",
        "loaded/classloader/ClassLoaderModule.kt",
    ],
    visibility = ["//visibility:public"],
    deps = [
        ":application",
        "//first_party/oksp/application/loaded/apploader:loader",
        "//first_party/oksp/application/loaded/apploader:impl_module",
        "@com_jackbradshaw_maven//:jakarta_inject_jakarta_inject_api",
    ],
)

alias(
    name = "component",
    actual = ":application",
    visibility = ["//visibility:public"],
)

kt_jvm_library(
    name = "test_lib",
    testonly = True,
    srcs = ["ApplicationTest.kt"],
    visibility = ["//visibility:public"],
    deps = [
        ":application",
        "//first_party/coroutines",
        "@com_jackbradshaw_maven//:junit_junit",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlin_kotlin_test",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)
