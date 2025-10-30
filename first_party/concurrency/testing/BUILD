load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")

kt_jvm_library(
    name = "testing",
    srcs = ["TestConcurrency.kt"],
    visibility = ["//first_party:__subpackages__"],
    deps = [
        "//:dagger",
        "//first_party/concurrency",
        "//first_party/concurrency:scope",
        "//first_party/concurrency/pulsar",
        "//first_party/concurrency/pulsar:module",
        "//first_party/concurrency/pulsar/testing",
        "//first_party/concurrency/pulsar/testing:module",
    ],
)
