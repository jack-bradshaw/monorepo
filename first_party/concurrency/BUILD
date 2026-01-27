load("//first_party/dagger:defs.bzl", 'kt_jvm_library_with_dagger')
load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")

kt_jvm_library_with_dagger(
    name = "concurrency",
    srcs = ["Concurrency.kt"],
    visibility = ["//first_party:__subpackages__"],
    deps = [
        ":scope",
        "//first_party/concurrency/pulsar",
        "//first_party/concurrency/pulsar:module",
    ],
)

kt_jvm_library_with_dagger(
    name = "scope",
    srcs = ["ConcurrencyScope.kt"],
    visibility = ["//visibility:public"],
    deps = [
    ],
)
