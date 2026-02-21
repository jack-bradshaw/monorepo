load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("//first_party/dagger:defs.bzl", "kt_jvm_library_with_dagger")

kt_jvm_library_with_dagger(
    name = "module",
    srcs = ["TestIoModule.kt"],
    visibility = ["//first_party/coroutines/testing:__pkg__"],
    deps = [
        "//first_party/coroutines:scope",
        "//first_party/coroutines/io",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_test",
    ],
)
