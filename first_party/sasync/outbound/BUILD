load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library", "kt_jvm_test")

kt_jvm_library(
    name = "scope",
    srcs = [
        "OutboundScope.kt",
    ],
    visibility = [
        "//visibility:public",
    ],
    deps = [
        "//:dagger",
    ],
)

kt_jvm_library(
    name = "outbound",
    srcs = [
        "Outbound.kt",
    ],
    visibility = [
        "//visibility:public",
    ],
    deps = [
        ":scope",
        "//:dagger",
        "//first_party/concurrency",
        "//first_party/coroutines",
        "//first_party/sasync/outbound/config:default",
        "//first_party/sasync/outbound/config:kt_jvm_proto",
        "//first_party/sasync/outbound/transport",
        "//first_party/sasync/outbound/transport:module",
    ],
)
