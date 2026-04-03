load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("//first_party/dagger:defs.bzl", "kt_jvm_library_with_dagger")
load("//first_party/publicity:publicity.bzl", "PUBLICITY")

kt_jvm_library_with_dagger(
    name = "testingtaskbarrier_module",
    srcs = ["TestingTaskBarrierModule.kt"],
    visibility = PUBLICITY,
    deps = [
        "//first_party/chronosphere/idleable",
        "//first_party/chronosphere/testingtaskbarrier",
        "//first_party/chronosphere/testingtaskbarrier:module",
        "//first_party/coroutines/cpu:module",
        "//first_party/coroutines/io:module",
        "//first_party/coroutines/testing",
        "//first_party/coroutines/testing/realistic/dispatcher",
    ],
)
