load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("//first_party/dagger:defs.bzl", "kt_jvm_library_with_dagger")

kt_jvm_library_with_dagger(
    name = "module",
    srcs = ["TestingTaskBarrierModule.kt"],
    visibility = ["//first_party/coroutines/testing/realistic:__subpackages__"],
    deps = [
        "//first_party/chronosphere/idleable",
        "//first_party/chronosphere/testingtaskbarrier",
        "//first_party/coroutines/cpu:module",
        "//first_party/coroutines/io:module",
        "//first_party/coroutines/testing:coroutines",
        "//first_party/coroutines/testing/realistic/dispatcher",
    ],
)
