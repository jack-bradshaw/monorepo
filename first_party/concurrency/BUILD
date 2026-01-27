load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("//first_party/dagger:defs.bzl", "kt_jvm_library_with_dagger")

kt_jvm_library_with_dagger(
    name = "scope",
    srcs = ["ConcurrencyScope.kt"],
    visibility = ["//visibility:public"],
)

kt_jvm_library(
    name = "component",
    srcs = ["ConcurrencyComponent.kt"],
    visibility = ["//visibility:public"],
    deps = [
        "//first_party/concurrency/pulsar",
    ],
)

kt_jvm_library_with_dagger(
    name = "component_impl",
    srcs = ["ConcurrencyComponentImpl.kt"],
    visibility = ["//first_party:__subpackages__"],
    deps = [
        ":component",
        ":scope",
        "//first_party/concurrency/pulsar:module",
    ],
)
