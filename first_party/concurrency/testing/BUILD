load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("//first_party/dagger:defs.bzl", "kt_jvm_library_with_dagger")

kt_jvm_library(
    name = "component",
    srcs = ["TestConcurrencyComponent.kt"],
    visibility = ["//visibility:public"],
    deps = [
        "//first_party/concurrency:component",
        "//first_party/concurrency/pulsar/testing",
    ],
)

kt_jvm_library_with_dagger(
    name = "component_impl",
    srcs = ["TestConcurrencyComponentImpl.kt"],
    visibility = ["//visibility:public"],
    deps = [
        ":component",
        "//first_party/concurrency:scope",
        "//first_party/concurrency/pulsar/testing:module",
    ],
)
