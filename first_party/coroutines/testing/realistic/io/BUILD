load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("//first_party/dagger:defs.bzl", "kt_jvm_library_with_dagger")

kt_jvm_library_with_dagger(
    name = "module",
    srcs = ["TestingIoModule.kt"],
    visibility = ["//first_party/coroutines/testing/realistic:__subpackages__"],
    deps = [
        "//first_party/chronosphere/idleable",
        "//first_party/coroutines:scope",
        "//first_party/coroutines/io:module",
        "//first_party/coroutines/testing/realistic/dispatcher",
        "//first_party/coroutines/testing/realistic/dispatcher:impl",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)
