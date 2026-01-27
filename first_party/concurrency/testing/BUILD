load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("//first_party/dagger:defs.bzl", "kt_jvm_library_with_dagger")

kt_jvm_library_with_dagger(
    name = "testing",
    srcs = ["TestConcurrency.kt"],
    visibility = ["//first_party:__subpackages__"],
    deps = [
        "//first_party/concurrency",
        "//first_party/concurrency:scope",
        "//first_party/concurrency/pulsar",
        "//first_party/concurrency/pulsar:module",
        "//first_party/concurrency/pulsar/testing",
        "//first_party/concurrency/pulsar/testing:module",
    ],
)
