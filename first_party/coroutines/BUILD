load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("//first_party/dagger:defs.bzl", "kt_jvm_library_with_dagger")
load(":publicity.bzl", "PUBLICITY")

kt_jvm_library_with_dagger(
    name = "scope",
    srcs = ["CoroutinesScope.kt"],
<<<<<<< HEAD
    visibility = [
        "//first_party/coroutines:__subpackages__",
    ],
)

kt_jvm_library(
    name = "component",
    srcs = ["CoroutinesComponent.kt"],
=======
>>>>>>> 013d49db (Refactor Backstab: Rename externaltypes, minimize targets, and polish docs)
    visibility = PUBLICITY,
    deps = [
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
