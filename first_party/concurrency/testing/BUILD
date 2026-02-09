load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("//first_party/concurrency:publicity.bzl", "PUBLICITY")
load("//first_party/dagger:defs.bzl", "kt_jvm_library_with_dagger")

kt_jvm_library(
    name = "component",
    srcs = ["TestConcurrencyComponent.kt"],
    visibility = PUBLICITY,
    deps = [
        "//first_party/concurrency:component",
        "//first_party/concurrency/pulsar/testing",
    ],
)

kt_jvm_library_with_dagger(
    name = "component_impl",
    srcs = ["TestConcurrencyComponentImpl.kt"],
    visibility = PUBLICITY,
    deps = [
        ":component",
        "//first_party/concurrency:scope",
        "//first_party/concurrency/pulsar/testing:module",
    ],
)
