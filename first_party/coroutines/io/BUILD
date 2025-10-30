load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")

kt_jvm_library(
    name = "io",
    srcs = ["Io.kt"],
    visibility = ["//first_party:__subpackages__"],
    deps = [
        "//:dagger",
    ],
)

kt_jvm_library(
    name = "module",
    srcs = ["IoModule.kt"],
    visibility = ["//first_party:__subpackages__"],
    deps = [
        ":io",
        "//:dagger",
        "//first_party/coroutines:scope",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)
