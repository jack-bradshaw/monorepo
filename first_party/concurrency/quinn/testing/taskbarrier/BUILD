load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("//first_party/concurrency/quinn:publicity.bzl", "PUBLICITY")
load("//first_party/dagger:defs.bzl", "kt_jvm_library_with_dagger")

kt_jvm_library_with_dagger(
    name = "module",
    srcs = ["TaskBarrierModule.kt"],
    visibility = [
        "//first_party/concurrency/quinn/testing:__subpackages__",
    ],
    deps = [
        "//first_party/chronosphere/testingtaskbarrier",
        "//first_party/concurrency/quinn:scope",
        "//first_party/concurrency/quinn:specific",
        "//first_party/concurrency/quinn/testing/hub",
    ],
)
