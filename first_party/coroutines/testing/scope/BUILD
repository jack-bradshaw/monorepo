load("//first_party/dagger:defs.bzl", 'kt_jvm_library_with_dagger')
load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")

kt_jvm_library_with_dagger(
    name = "module",
    srcs = ["ScopeModule.kt"],
    visibility = ["//first_party:__subpackages__"],
    deps = [
        "//first_party/coroutines:scope",
        "//first_party/coroutines/testing/dispatchers",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
        "@com_jackbradshaw_maven//:org_jetbrains_kotlinx_kotlinx_coroutines_test",
    ],
)
