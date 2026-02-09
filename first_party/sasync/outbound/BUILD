load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("//first_party/dagger:defs.bzl", "kt_jvm_library_with_dagger")
load("//first_party/sasync:publicity.bzl", "PUBLICITY")

kt_jvm_library_with_dagger(
    name = "scope",
    srcs = ["OutboundScope.kt"],
    visibility = ["//first_party/sasync/outbound:__subpackages__"],
)

kt_jvm_library(
    name = "component",
    srcs = ["OutboundComponent.kt"],
    visibility = PUBLICITY,
    deps = ["//first_party/sasync/outbound/transport"],
)

kt_jvm_library_with_dagger(
    name = "component_impl",
    srcs = ["OutboundComponentImpl.kt"],
    visibility = PUBLICITY,
    deps = [
        ":component",
        ":scope",
        "//first_party/concurrency:component",
        "//first_party/concurrency:component_impl",
        "//first_party/coroutines:component",
        "//first_party/coroutines:component_impl",
        "//first_party/sasync/outbound/config:kt_jvm_proto",
        "//first_party/sasync/outbound/transport:module",
    ],
)
