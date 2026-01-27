load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("//first_party/dagger:defs.bzl", "kt_jvm_library_with_dagger")

kt_jvm_library_with_dagger(
    name = "scope",
    srcs = ["CoroutinesScope.kt"],
    visibility = ["//visibility:public"],
    deps = [
    ],
)

kt_jvm_library_with_dagger(
    name = "coroutines",
    srcs = ["Coroutines.kt"],
    visibility = ["//first_party:__subpackages__"],
    deps = [
        ":scope",
        "//first_party/coroutines/io",
        "//first_party/coroutines/io:module",
    ],
)
