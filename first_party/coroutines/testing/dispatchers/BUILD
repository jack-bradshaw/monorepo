load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("//first_party/coroutines:publicity.bzl", "PUBLICITY")
load("//first_party/dagger:defs.bzl", "kt_jvm_library_with_dagger")

kt_jvm_library_with_dagger(
    name = "dispatchers",
    srcs = ["Dispatchers.kt"],
    visibility = PUBLICITY,
)

kt_jvm_library_with_dagger(
    name = "module",
    srcs = ["DispatchersModule.kt"],
    visibility = ["//first_party/coroutines/testing:__subpackages__"],
    deps = [
        ":dispatchers",
        "//first_party/coroutines:scope",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_test",
    ],
)
