load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("//first_party/dagger:defs.bzl", "kt_jvm_library_with_dagger")
load("//first_party/publicity:publicity.bzl", "PUBLICITY")

kt_jvm_library_with_dagger(
    name = "testingtaskdriver_module",
    srcs = ["TestingTaskDriverModule.kt"],
    visibility = PUBLICITY,
    deps = [
        "//first_party/chronosphere/testingtaskdriver",
        "//first_party/chronosphere/testingtaskdriver:module",
        "//first_party/coroutines/testing",
        "//first_party/coroutines/testing/artificial/dispatcher",
    ],
)
