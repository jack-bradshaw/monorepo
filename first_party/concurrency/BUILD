load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("//first_party/dagger:defs.bzl", "kt_jvm_library_with_dagger")
load(":publicity.bzl", "PUBLICITY")

kt_jvm_library_with_dagger(
    name = "scope",
    srcs = ["ConcurrencyScope.kt"],
    visibility = PUBLICITY,
)

kt_jvm_library(
    name = "component",
    srcs = ["ConcurrencyComponent.kt"],
    visibility = PUBLICITY,
    deps = ["//first_party/concurrency/pulsar"],
)

kt_jvm_library_with_dagger(
    name = "component_impl",
    srcs = ["ConcurrencyComponentImpl.kt"],
    visibility = PUBLICITY,
    deps = [
        ":component",
        ":scope",
        "//first_party/concurrency/pulsar:module",
    ],
)
