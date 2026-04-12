load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("//first_party/dagger:defs.bzl", "kt_jvm_library_with_dagger")
load("//first_party/publicity:publicity.bzl", "PUBLICITY")

kt_jvm_library_with_dagger(
    name = "module",
    srcs = ["TestingTaskDriverModule.kt"],
    visibility = ["//first_party/coroutines/testing/artificial:__subpackages__"],
    deps = [
        "//first_party/chronosphere/testingtaskdriver",
        "//first_party/coroutines/testing:coroutines",
        "//first_party/coroutines/testing/artificial/dispatcher",
    ],
)
