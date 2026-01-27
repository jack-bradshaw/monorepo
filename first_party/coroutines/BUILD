load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("//first_party/dagger:defs.bzl", "kt_jvm_library_with_dagger")

kt_jvm_library_with_dagger(
    name = "scope",
    srcs = ["CoroutinesScope.kt"],
    visibility = ["//first_party:__subpackages__"],
)

kt_jvm_library(
    name = "component",
    srcs = ["CoroutinesComponent.kt"],
    visibility = ["//first_party:__subpackages__"],
    deps = [
        "//first_party/coroutines/io",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_library_with_dagger(
    name = "component_impl",
    srcs = ["CoroutinesComponentImpl.kt"],
    visibility = ["//first_party:__subpackages__"],
    deps = [
        ":component",
        ":scope",
        "//first_party/coroutines/io:module",
    ],
)
