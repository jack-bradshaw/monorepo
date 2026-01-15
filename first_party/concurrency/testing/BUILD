load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")

kt_jvm_library(
    name = "component",
    srcs = ["TestConcurrencyComponent.kt"],
    visibility = ["//visibility:public"],
    deps = [
        "//first_party/concurrency:component",
        "//first_party/concurrency/pulsar/testing",
    ],
)

kt_jvm_library(
    name = "component_impl",
    srcs = ["TestConcurrencyComponentImpl.kt"],
    visibility = ["//visibility:public"],
    deps = [
        ":component",
        "//:dagger",
        "//first_party/concurrency:scope",
        "//first_party/concurrency/pulsar/testing:module",
    ],
)
