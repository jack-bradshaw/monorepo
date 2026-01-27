load("//first_party/dagger:defs.bzl", 'kt_jvm_library_with_dagger')
load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library", "kt_jvm_test")

kt_jvm_library_with_dagger(
    name = "scope",
    srcs = [
        "InboundScope.kt",
    ],
    visibility = [
        "//visibility:public",
    ],
    deps = [
    ],
)

kt_jvm_library_with_dagger(
    name = "inbound",
    srcs = [
        "Inbound.kt",
    ],
    visibility = [
        "//visibility:public",
    ],
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
