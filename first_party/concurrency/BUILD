load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")

kt_jvm_library(
    name = "scope",
    srcs = ["ConcurrencyScope.kt"],
    visibility = ["//first_party:__subpackages__"],
    deps = [
        "//:dagger",
    ],
)
