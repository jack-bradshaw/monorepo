load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("//first_party/dagger:defs.bzl", "kt_jvm_library_with_dagger")

kt_jvm_library(
    name = "component",
    srcs = ["TestCoroutinesComponent.kt"],
    visibility = ["//visibility:public"],
    deps = [
        "//first_party/coroutines:component",
        "//first_party/coroutines/testing/launcher",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_test",
    ],
)

kt_jvm_library_with_dagger(
    name = "component_impl",
    srcs = ["TestCoroutinesComponentImpl.kt"],
    visibility = ["//visibility:public"],
    deps = [
        ":component",
        "//first_party/coroutines:scope",
        "//first_party/coroutines/io/testing:module",
        "//first_party/coroutines/testing/launcher:module",
        "//first_party/coroutines/testing/scope:module",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_test",
    ],
)
