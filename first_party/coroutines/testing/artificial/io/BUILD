load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("//first_party/dagger:defs.bzl", "kt_jvm_library_with_dagger")
load("//first_party/publicity:publicity.bzl", "PUBLICITY")

kt_jvm_library_with_dagger(
    name = "module",
    srcs = ["TestingIoModule.kt"],
    visibility = ["//first_party/coroutines/testing/artificial:__subpackages__"],
    deps = [
        "//first_party/coroutines/io:module",
        "//first_party/coroutines/testing/artificial/dispatcher",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)
