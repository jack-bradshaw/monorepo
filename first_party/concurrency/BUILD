load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("//first_party/dagger:defs.bzl", "kt_jvm_library_with_dagger")
load(":publicity.bzl", "PUBLICITY")

kt_jvm_library_with_dagger(
    name = "concurrency",
    srcs = ["Concurrency.kt"],
    visibility = PUBLICITY,
    deps = [
        ":scope",
        "//first_party/concurrency/pulsar",
        "//first_party/concurrency/pulsar:module",
    ],
)

kt_jvm_library_with_dagger(
    name = "scope",
    srcs = ["ConcurrencyScope.kt"],
    visibility = PUBLICITY,
)
