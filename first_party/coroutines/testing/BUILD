load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")

kt_jvm_library(
    name = "testing",
    srcs = ["TestCoroutines.kt"],
    visibility = ["//first_party:__subpackages__"],
    deps = [
        "//:dagger",
        "//first_party/coroutines",
        "//first_party/coroutines:scope",
        "//first_party/coroutines/io",
        "//first_party/coroutines/io/testing:module",
        "//first_party/coroutines/testing/launcher:module",
    ],
)
