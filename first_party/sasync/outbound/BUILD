load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")

kt_jvm_library(
    name = "scope",
    srcs = ["OutboundScope.kt"],
    visibility = ["//visibility:public"],
    deps = ["//:dagger"],
)

kt_jvm_library(
    name = "component",
    srcs = ["OutboundComponent.kt"],
    visibility = ["//visibility:public"],
    deps = [
        "//first_party/sasync/outbound/transport",
    ],
)

kt_jvm_library(
    name = "component_impl",
    srcs = ["OutboundComponentImpl.kt"],
    visibility = ["//first_party:__subpackages__"],
    deps = [
        ":component",
        ":scope",
        "//:dagger",
        "//first_party/concurrency:component",
        "//first_party/concurrency:component_impl",
        "//first_party/coroutines:component",
        "//first_party/coroutines:component_impl",
        "//first_party/sasync/outbound/config:kt_jvm_proto",
        "//first_party/sasync/outbound/transport:module",
    ],
)
