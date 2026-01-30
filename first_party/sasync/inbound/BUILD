load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("//first_party/dagger:defs.bzl", "kt_jvm_library_with_dagger")

kt_jvm_library_with_dagger(
    name = "scope",
    srcs = ["InboundScope.kt"],
    visibility = ["//visibility:public"],
)

kt_jvm_library(
    name = "component",
    srcs = ["InboundComponent.kt"],
    visibility = ["//visibility:public"],
    deps = [
        "//first_party/sasync/inbound/transport",
    ],
)

kt_jvm_library_with_dagger(
    name = "component_impl",
    srcs = ["InboundComponentImpl.kt"],
    visibility = ["//first_party:__subpackages__"],
    deps = [
        ":component",
        ":scope",
        "//first_party/concurrency:component",
        "//first_party/concurrency:component_impl",
        "//first_party/coroutines:component",
        "//first_party/coroutines:component_impl",
        "//first_party/sasync/inbound/config:kt_jvm_proto",
        "//first_party/sasync/inbound/transport:module",
    ],
)
