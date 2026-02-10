load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("//first_party/coroutines:publicity.bzl", "PUBLICITY")
load("//first_party/dagger:defs.bzl", "kt_jvm_library_with_dagger")

kt_jvm_library_with_dagger(
    name = "testing",
    srcs = ["TestCoroutines.kt"],
    visibility = PUBLICITY,
    deps = [
        "//first_party/coroutines",
        "//first_party/coroutines:scope",
        "//first_party/coroutines/io",
        "//first_party/coroutines/io/testing:module",
        "//first_party/coroutines/testing/dispatchers:module",
        "//first_party/coroutines/testing/launcher:module",
    ],
)
