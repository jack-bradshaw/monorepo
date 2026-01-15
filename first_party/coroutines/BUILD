load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")

kt_jvm_library(
    name = "scope",
    srcs = ["CoroutinesScope.kt"],
    visibility = ["//first_party:__subpackages__"],
    deps = ["//:dagger"],
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

kt_jvm_library(
    name = "component_impl",
    srcs = ["CoroutinesComponentImpl.kt"],
    visibility = ["//first_party:__subpackages__"],
    deps = [
        ":component",
        ":scope",
        "//:dagger",
        "//first_party/coroutines/io:module",
    ],
)
