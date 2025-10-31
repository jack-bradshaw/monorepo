load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")

kt_jvm_library(
    name = "concurrency",
    srcs = ["Concurrency.kt"],
    visibility = ["//first_party:__subpackages__"],
    deps = [
        ":scope",
        "//:dagger",
        "//first_party/concurrency/pulsar",
        "//first_party/concurrency/pulsar:module",
    ],
)

kt_jvm_library(
    name = "scope",
    srcs = ["ConcurrencyScope.kt"],
    visibility = ["//visibility:public"],
    deps = [
        "//:dagger",
    ],
)
