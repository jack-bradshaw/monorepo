load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")

kt_jvm_library(
    name = "scope",
    srcs = ["CoroutinesScope.kt"],
    visibility = ["//visibility:public"],
    deps = [
        "//:dagger",
    ],
)

kt_jvm_library(
    name = "coroutines",
    srcs = ["Coroutines.kt"],
    visibility = ["//first_party:__subpackages__"],
    deps = [
        ":scope",
        "//:dagger",
        "//first_party/coroutines/io",
        "//first_party/coroutines/io:module",
    ],
)
