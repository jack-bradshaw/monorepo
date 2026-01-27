load("//first_party/dagger:defs.bzl", 'kt_jvm_library_with_dagger')
load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")

kt_jvm_library_with_dagger(
    name = "testing",
    srcs = ["TestCoroutines.kt"],
    visibility = ["//first_party:__subpackages__"],
    deps = [
        "//first_party/coroutines",
        "//first_party/coroutines:scope",
        "//first_party/coroutines/io",
        "//first_party/coroutines/io/testing:module",
        "//first_party/coroutines/testing/launcher:module",
    ],
)
