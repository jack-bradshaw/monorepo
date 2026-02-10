load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library", "kt_jvm_test")
load("//first_party/dagger:defs.bzl", "kt_jvm_library_with_dagger")
load("//first_party/sasync:publicity.bzl", "PUBLICITY")

kt_jvm_library_with_dagger(
    name = "scope",
    srcs = [
        "InboundScope.kt",
    ],
    visibility = PUBLICITY,
)

kt_jvm_library_with_dagger(
    name = "inbound",
    srcs = [
        "Inbound.kt",
    ],
    visibility = PUBLICITY,
    deps = [
        ":scope",
        "//first_party/concurrency",
        "//first_party/coroutines",
        "//first_party/sasync/inbound/config:default",
        "//first_party/sasync/inbound/config:kt_jvm_proto",
        "//first_party/sasync/inbound/transport",
        "//first_party/sasync/inbound/transport:module",
    ],
)
