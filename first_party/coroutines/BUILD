load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("//first_party/dagger:defs.bzl", "kt_jvm_library_with_dagger")
load(":publicity.bzl", "PUBLICITY")

kt_jvm_library_with_dagger(
    name = "scope",
    srcs = ["CoroutinesScope.kt"],
    visibility = [
        "//first_party/coroutines:__subpackages__",
    ],
)

kt_jvm_library_with_dagger(
    name = "coroutines",
    srcs = ["Coroutines.kt"],
    visibility = PUBLICITY,
    deps = [
        ":scope",
        "//first_party/coroutines/io",
        "//first_party/coroutines/io:module",
    ],
)
