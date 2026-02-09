load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("//first_party/dagger:defs.bzl", "kt_jvm_library_with_dagger")
load(":publicity.bzl", "PUBLICITY")

kt_jvm_library_with_dagger(
    name = "scope",
    srcs = ["CoroutinesScope.kt"],
    visibility = [
        "//first_party/coroutines:__subpackages__",
    ],
)

kt_jvm_library(
    name = "component",
    srcs = ["CoroutinesComponent.kt"],
    visibility = PUBLICITY,
    deps = [
        "//first_party/coroutines/io",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_library_with_dagger(
    name = "component_impl",
    srcs = ["CoroutinesComponentImpl.kt"],
    visibility = PUBLICITY,
    deps = [
        ":component",
        ":scope",
        "//first_party/coroutines/io:module",
    ],
)
