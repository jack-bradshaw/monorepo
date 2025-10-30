load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")

kt_jvm_library(
    name = "module",
    srcs = ["TestIoModule.kt"],
    visibility = ["//first_party:__subpackages__"],
    deps = [
        "//:dagger",
        "//first_party/coroutines:scope",
        "//first_party/coroutines/io",
        "//first_party/coroutines/testing/scope:module",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_test",
    ],
)
