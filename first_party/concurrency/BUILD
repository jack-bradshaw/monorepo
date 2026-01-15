load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")

kt_jvm_library(
    name = "scope",
    srcs = ["ConcurrencyScope.kt"],
    visibility = ["//visibility:public"],
    deps = ["//:dagger"],
)

kt_jvm_library(
    name = "component",
    srcs = ["ConcurrencyComponent.kt"],
    visibility = ["//visibility:public"],
    deps = [
        "//first_party/concurrency/pulsar",
    ],
)

kt_jvm_library(
    name = "component_impl",
    srcs = ["ConcurrencyComponentImpl.kt"],
    visibility = ["//first_party:__subpackages__"],
    deps = [
        ":component",
        ":scope",
        "//:dagger",
        "//first_party/concurrency/pulsar:module",
    ],
)
